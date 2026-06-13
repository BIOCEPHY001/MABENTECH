// ============================================
// MABENTECH — Main JavaScript
// ============================================

// ── STATE ──────────────────────────────────
let cart = JSON.parse(localStorage.getItem('mbt_cart') || '[]');
let wishlist = JSON.parse(localStorage.getItem('mbt_wish') || '[]');

// ── INIT ────────────────────────────────────
document.addEventListener('DOMContentLoaded', () => {
  updateBadges();
  initNavScroll();
  initSearchSuggestions();
  renderCartDrawer();
  renderWishDrawer();
});

// ── NAVBAR ──────────────────────────────────
function initNavScroll() {
  const nav = document.getElementById('navbar');
  if (!nav) return;
  window.addEventListener('scroll', () => {
    nav.classList.toggle('scrolled', window.scrollY > 20);
  }, { passive: true });
}

function toggleMobileMenu() {
  document.getElementById('mobileMenu')?.classList.toggle('open');
}

// ── SEARCH ──────────────────────────────────
function initSearchSuggestions() {
  const input = document.getElementById('navSearch');
  const suggestions = document.getElementById('searchSuggestions');
  if (!input || !suggestions) return;

  let debounceTimer;
  input.addEventListener('input', () => {
    clearTimeout(debounceTimer);
    const q = input.value.trim();
    if (q.length < 2) { suggestions.style.display = 'none'; return; }
    debounceTimer = setTimeout(async () => {
      try {
        const res = await fetch(`/api/search?q=${encodeURIComponent(q)}`);
        const products = await res.json();
        if (!products.length) { suggestions.style.display = 'none'; return; }
        suggestions.innerHTML = products.slice(0, 6).map(p => `
          <a href="/product/${p.id}" class="suggestion-item">
            <span class="sug-emoji">${getCatEmoji(p.category, p.subcategory)}</span>
            <span class="sug-info">
              <span class="sug-name">${p.name}</span>
              <span class="sug-brand">${p.brand} · $${p.price}</span>
            </span>
          </a>`).join('');
        suggestions.style.display = 'block';
      } catch (e) { suggestions.style.display = 'none'; }
    }, 280);
  });

  document.addEventListener('click', e => {
    if (!input.contains(e.target)) suggestions.style.display = 'none';
  });
}

// ── CART ────────────────────────────────────
function addToCart(id, name, price, brand) {
  const existing = cart.find(i => i.id === id);
  if (existing) {
    existing.qty += 1;
  } else {
    cart.push({ id, name, price: parseFloat(price), brand, qty: 1 });
  }
  saveCart();
  renderCartDrawer();
  updateBadges();
  showToast(`<i class="fas fa-check-circle" style="color:#00d4a1"></i> ${name} added to cart`, 'success');

  // Animate button
  const btns = document.querySelectorAll(`[onclick*="addToCart(${id},"]`);
  btns.forEach(btn => {
    btn.classList.add('added');
    setTimeout(() => btn.classList.remove('added'), 1500);
  });
}

function removeFromCart(id) {
  cart = cart.filter(i => i.id !== id);
  saveCart();
  renderCartDrawer();
  updateBadges();
}

function changeQty(id, delta) {
  const item = cart.find(i => i.id === id);
  if (!item) return;
  item.qty = Math.max(0, item.qty + delta);
  if (item.qty === 0) cart = cart.filter(i => i.id !== id);
  saveCart();
  renderCartDrawer();
  updateBadges();
}

function saveCart() {
  localStorage.setItem('mbt_cart', JSON.stringify(cart));
}

function renderCartDrawer() {
  const body = document.getElementById('cartDrawerBody');
  const footer = document.getElementById('cartDrawerFooter');
  const countEl = document.getElementById('cartItemCount');
  if (!body) return;

  const total = cart.reduce((s, i) => s + i.qty, 0);
  if (countEl) countEl.textContent = total ? `(${total} item${total !== 1 ? 's' : ''})` : '';

  if (!cart.length) {
    body.innerHTML = `<div class="cart-empty-state">
      <i class="fas fa-shopping-bag"></i>
      <p>Your cart is empty</p>
      <a href="/shop" class="btn btn-primary btn-sm" onclick="closeDrawers()">Start Shopping</a>
    </div>`;
    if (footer) footer.innerHTML = '';
    return;
  }

  body.innerHTML = cart.map(item => `
    <div class="cart-item">
      <div class="cart-item-img">${getCatEmoji('laptop', '')}</div>
      <div class="cart-item-info">
        <div class="cart-item-brand">${item.brand}</div>
        <div class="cart-item-name">${item.name}</div>
        <div class="cart-item-price">$${(item.price * item.qty).toLocaleString('en-US', {minimumFractionDigits:2})}</div>
        <div class="cart-item-qty">
          <button class="qty-btn" onclick="changeQty(${item.id}, -1)">−</button>
          <span class="qty-num">${item.qty}</span>
          <button class="qty-btn" onclick="changeQty(${item.id}, 1)">+</button>
        </div>
      </div>
      <button class="cart-item-remove" onclick="removeFromCart(${item.id})" aria-label="Remove">
        <i class="fas fa-trash-alt"></i>
      </button>
    </div>`).join('');

  const subtotal = cart.reduce((s, i) => s + i.price * i.qty, 0);
  const shipping = subtotal >= 500 ? 0 : 29.99;
  const total2 = subtotal + shipping;

  if (footer) {
    footer.innerHTML = `
      <div class="cart-summary">
        <div class="cart-summary-row"><span>Subtotal</span><span>$${subtotal.toFixed(2)}</span></div>
        <div class="cart-summary-row"><span>Shipping</span><span>${shipping === 0 ? '<span style="color:#00d4a1">Free</span>' : '$' + shipping.toFixed(2)}</span></div>
        <div class="cart-summary-row"><span>Total</span><span>$${total2.toFixed(2)}</span></div>
      </div>
      <a href="/checkout" class="btn btn-primary btn-checkout btn-full" onclick="closeDrawers()">
        <i class="fas fa-lock"></i> Secure Checkout
      </a>
      <a href="/cart" style="display:block;text-align:center;margin-top:.75rem;font-size:.85rem;color:var(--text3)" onclick="closeDrawers()">View Full Cart</a>`;
  }
}

function toggleCart() {
  const drawer = document.getElementById('cartDrawer');
  const wishD = document.getElementById('wishDrawer');
  wishD?.classList.remove('open');
  drawer?.classList.toggle('open');
  document.getElementById('overlay')?.classList.toggle('open', drawer?.classList.contains('open'));
}

// ── WISHLIST ────────────────────────────────
function toggleWishItem(id) {
  fetch(`/api/products/${id}`).then(r => r.json()).then(p => {
    const idx = wishlist.findIndex(i => i.id === id);
    if (idx > -1) {
      wishlist.splice(idx, 1);
      showToast('<i class="far fa-heart" style="color:#ff4d6d"></i> Removed from wishlist');
    } else {
      wishlist.push({ id: p.id, name: p.name, price: p.price, brand: p.brand, subcategory: p.subcategory, category: p.category });
      showToast(`<i class="fas fa-heart" style="color:#ff4d6d"></i> ${p.name} wishlisted`);
    }
    localStorage.setItem('mbt_wish', JSON.stringify(wishlist));
    updateBadges();
    renderWishDrawer();
    updateWishButtons();
  }).catch(() => showToast('Something went wrong', 'error'));
}

function renderWishDrawer() {
  const body = document.getElementById('wishDrawerBody');
  if (!body) return;
  if (!wishlist.length) {
    body.innerHTML = `<div class="cart-empty-state">
      <i class="far fa-heart"></i>
      <p>Your wishlist is empty</p>
      <a href="/shop" class="btn btn-primary btn-sm" onclick="closeDrawers()">Discover Products</a>
    </div>`;
    return;
  }
  body.innerHTML = wishlist.map(item => `
    <div class="cart-item">
      <div class="cart-item-img">${getCatEmoji(item.category, item.subcategory)}</div>
      <div class="cart-item-info">
        <div class="cart-item-brand">${item.brand}</div>
        <div class="cart-item-name">${item.name}</div>
        <div class="cart-item-price">$${parseFloat(item.price).toLocaleString('en-US', {minimumFractionDigits:2})}</div>
        <div style="margin-top:.5rem">
          <button onclick="addToCart(${item.id}, '${item.name.replace(/'/g,"\\'")}', ${item.price}, '${item.brand}'); removeFromWish(${item.id})"
                  class="btn btn-primary btn-sm">
            <i class="fas fa-cart-plus"></i> Add to Cart
          </button>
        </div>
      </div>
      <button class="cart-item-remove" onclick="removeFromWish(${item.id})" aria-label="Remove">
        <i class="fas fa-times"></i>
      </button>
    </div>`).join('');
}

function removeFromWish(id) {
  wishlist = wishlist.filter(i => i.id !== id);
  localStorage.setItem('mbt_wish', JSON.stringify(wishlist));
  updateBadges();
  renderWishDrawer();
  updateWishButtons();
}

function toggleWishlist() {
  const drawer = document.getElementById('wishDrawer');
  const cartD = document.getElementById('cartDrawer');
  cartD?.classList.remove('open');
  drawer?.classList.toggle('open');
  document.getElementById('overlay')?.classList.toggle('open', drawer?.classList.contains('open'));
}

function updateWishButtons() {
  document.querySelectorAll('.action-btn.wish-btn').forEach(btn => {
    const onclick = btn.getAttribute('onclick') || '';
    const match = onclick.match(/\d+/);
    if (match) {
      const id = parseInt(match[0]);
      btn.classList.toggle('wishlisted', wishlist.some(i => i.id === id));
    }
  });
}

// ── BADGES ──────────────────────────────────
function updateBadges() {
  const cartTotal = cart.reduce((s, i) => s + i.qty, 0);
  const cartBadge = document.getElementById('cartBadge');
  if (cartBadge) {
    cartBadge.textContent = cartTotal;
    cartBadge.style.display = cartTotal > 0 ? 'flex' : 'none';
  }
  const wishBadge = document.getElementById('wishBadge');
  if (wishBadge) {
    wishBadge.textContent = wishlist.length;
    wishBadge.style.display = wishlist.length > 0 ? 'flex' : 'none';
  }
}

// ── DRAWERS CLOSE ────────────────────────────
function closeDrawers() {
  document.getElementById('cartDrawer')?.classList.remove('open');
  document.getElementById('wishDrawer')?.classList.remove('open');
  document.getElementById('overlay')?.classList.remove('open');
}

// ── TOAST ────────────────────────────────────
function showToast(message, type = 'default') {
  const container = document.getElementById('toastContainer');
  if (!container) return;
  const toast = document.createElement('div');
  toast.className = `toast toast-${type}`;
  toast.innerHTML = message;
  container.appendChild(toast);
  setTimeout(() => toast.remove(), 3200);
}

// ── NEWSLETTER ───────────────────────────────
function subscribeNewsletter(e) {
  e.preventDefault();
  const input = e.target.querySelector('input');
  if (!input?.value) return;
  showToast('<i class="fas fa-check-circle" style="color:#00d4a1"></i> Subscribed! Welcome to MABENTECH.', 'success');
  input.value = '';
}

// ── HELPERS ──────────────────────────────────
function getCatEmoji(category, subcategory) {
  const map = { keyboard: '⌨️', mouse: '🖱️', monitor: '🖥️', storage: '💾', dock: '🔌', bag: '👜', charger: '⚡' };
  return map[subcategory] || (category === 'laptop' ? '💻' : '🔧');
}

// ── SHOP PAGE FILTERS ────────────────────────
function filterProducts(cat) {
  document.querySelectorAll('.filter-chip').forEach(c => c.classList.remove('active'));
  event?.target?.classList.add('active');
  const cards = document.querySelectorAll('.product-card');
  cards.forEach(card => {
    const cardCat = card.dataset.category || '';
    card.style.display = (cat === 'all' || cardCat === cat) ? '' : 'none';
  });
}

// Search suggestions inline styles injection
const style = document.createElement('style');
style.textContent = `
  .search-suggestions{position:absolute;top:calc(100% + 6px);left:0;right:0;background:var(--surface);border:1.5px solid var(--border2);border-radius:var(--radius-lg);overflow:hidden;box-shadow:var(--shadow-lg);z-index:500;display:none}
  .suggestion-item{display:flex;align-items:center;gap:.75rem;padding:.75rem 1rem;font-size:.875rem;color:var(--text2);transition:background var(--transition)}
  .suggestion-item:hover{background:var(--surface2);color:var(--text)}
  .sug-emoji{font-size:1.4rem;flex-shrink:0}
  .sug-info{display:flex;flex-direction:column}
  .sug-name{font-weight:600;color:var(--text);font-size:.85rem}
  .sug-brand{font-size:.75rem;color:var(--text3)}
`;
document.head.appendChild(style);
