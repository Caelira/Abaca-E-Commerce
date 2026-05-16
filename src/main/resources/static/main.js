// --- MODAL LOGIC ---
function openModal(modalId) {
    document.querySelectorAll('.modal-overlay').forEach(m => m.style.display = 'none'); // Close others
    const modal = document.getElementById(modalId);
    if(modal) {
        modal.style.display = 'flex';
        // Auto-hide inner forms for Auth Modal
        if(modalId === 'authModal') toggleAuth('loginForm');
    }
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if(modal) modal.style.display = 'none';
}

function toggleAuth(formId) {
    const login = document.getElementById('loginForm');
    const signup = document.getElementById('signupForm');
    if(login && signup) {
        login.style.display = formId === 'loginForm' ? 'block' : 'none';
        signup.style.display = formId === 'signupForm' ? 'block' : 'none';
    }
}

// --- CART LOGIC ---
function initCartLogic() {
    const checkboxes = document.querySelectorAll('.item-checkbox');
    const topSelectAll = document.getElementById('selectAllTop');
    const bottomSelectAll = document.getElementById('selectAllBottom');

    if(!topSelectAll) return; // Not on cart page

    function updateCartTotal() {
        let total = 0, count = 0;
        checkboxes.forEach(cb => {
            if (cb.checked) {
                total += parseFloat(cb.getAttribute('data-price'));
                count++;
            }
        });
        document.getElementById('cartTotalPrice').innerText = total.toFixed(2);
        document.getElementById('totalItemsCount').innerText = count;
    }

    checkboxes.forEach(cb => cb.addEventListener('change', updateCartTotal));

    function syncSelectAll(state) {
        if(topSelectAll) topSelectAll.checked = state;
        if(bottomSelectAll) bottomSelectAll.checked = state;
        checkboxes.forEach(cb => cb.checked = state);
        updateCartTotal();
    }

    topSelectAll.addEventListener('change', function() { syncSelectAll(this.checked); });
    if(bottomSelectAll) bottomSelectAll.addEventListener('change', function() { syncSelectAll(this.checked); });
}

// Initialize scripts on load
document.addEventListener('DOMContentLoaded', () => {
    initCartLogic();
    });