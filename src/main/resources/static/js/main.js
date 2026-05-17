document.addEventListener("DOMContentLoaded", function () {
    // Initialize 3D Tilt Effect
    if (typeof VanillaTilt !== 'undefined') {
        VanillaTilt.init(document.querySelectorAll("[data-tilt]"), {
            max: 12,
            speed: 400,
            glare: true,
            "max-glare": 0.15,
            scale: 1.02
        });
    }

    // URL Notification Interceptor
    const urlParams = new URLSearchParams(window.location.search);
    let paramFound = false;

    if (urlParams.has('registered')) {
        showNotification('Account created successfully! Please log in.', 'success');
        paramFound = true;
    }
    if (urlParams.has('added')) {
        showNotification('Item added to your shopping cart!', 'success');
        paramFound = true;
    }
    if (urlParams.has('profileSuccess')) {
        showNotification('Your profile was updated successfully!', 'success');
        paramFound = true;
    }
    if (urlParams.has('passwordSuccess')) {
        showNotification('Password changed securely!', 'success');
        paramFound = true;
    }
    if (urlParams.has('orderSuccess')) {
        showNotification('Order placed successfully! Thank you.', 'success');
        paramFound = true;
    }
    if (urlParams.has('error')) {
        let errorMsg = urlParams.get('error');
        if (errorMsg === 'true' || errorMsg === '') {
            errorMsg = 'Operation failed. Please check your inputs.';
        }
        showNotification(errorMsg, 'error');
        paramFound = true;
    }

    // Clean the URL so notifications don't fire again on refresh
    if (paramFound) {
        const cleanUrl = window.location.pathname;
        window.history.replaceState({}, document.title, cleanUrl);
    }
});

// Toast Notification Engine
function showNotification(message, type = 'success') {
    let container = document.getElementById('toast-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toast-container';
        container.className = 'toast-container';
        document.body.appendChild(container);
    }
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    const icon = type === 'success' ? '' : '';
    toast.innerHTML = `
        <div class="toast-icon">${icon}</div>
        <div class="toast-message">${message}</div>
    `;
    container.appendChild(toast);

    setTimeout(() => toast.classList.add('show'), 10);

    setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => toast.remove(), 400);
    }, 3500);
}

// ----------------------------------------------------
// THE FIX: HTML Fragment Fetch Logic
// ----------------------------------------------------
let homeFeedPage = 1;

function loadNextProductBatch() {
    const button = document.getElementById('loadMoreBtn');
    if (button) {
        button.innerText = 'Loading...';
        button.disabled = true;
        button.style.opacity = '0.7';
    }

    // Fetch the HTML Fragment from the Controller
    fetch(`/products/feed?page=${homeFeedPage}`)
        .then(response => {
            if (!response.ok) throw new Error('Network response was not ok');
            return response.text(); // Parse as Text/HTML, NOT JSON
        })
        .then(htmlFragment => {
            // If the fragment is tiny, it means there are no more products
            if (htmlFragment.trim().length < 20) {
                if (button) button.style.display = 'none';
                return;
            }

            // Append the HTML fragment directly into the Grid
            const grid = document.getElementById('productGrid');
            if (grid) {
                grid.insertAdjacentHTML('beforeend', htmlFragment);
            }

            // Successfully loaded, queue up the next page
            homeFeedPage++;

            // Re-apply the VanillaTilt 3D effect ONLY to the newly injected cards
            if (typeof VanillaTilt !== 'undefined') {
                VanillaTilt.init(document.querySelectorAll("#productGrid .product-card:not(.js-tilt)"), {
                    max: 12, speed: 400, glare: true, "max-glare": 0.15, scale: 1.02
                });
            }

            // Restore button state
            if (button) {
                button.innerText = 'Load More';
                button.disabled = false;
                button.style.opacity = '1';
            }
        })
        .catch(err => {
            console.error('Error fetching more products:', err);
            if (button) {
                button.innerText = 'Try Again';
                button.disabled = false;
                button.style.opacity = '1';
            }
        });
}

// ----------------------------------------------------
// Modal Controls
// ----------------------------------------------------
function openLoginModal() {
    closeSignupModal();
    const modal = document.getElementById('authLoginModal');
    if (modal) modal.style.display = 'flex';
}

function closeLoginModal() {
    const modal = document.getElementById('authLoginModal');
    if (modal) modal.style.display = 'none';
}

function openSignupModal() {
    closeLoginModal();
    const modal = document.getElementById('authSignupModal');
    if (modal) modal.style.display = 'flex';
}

function closeSignupModal() {
    const modal = document.getElementById('authSignupModal');
    if (modal) modal.style.display = 'none';
}

// Close modals when clicking on the dark background mask
window.addEventListener('click', function(event) {
    const loginM = document.getElementById('authLoginModal');
    const signupM = document.getElementById('authSignupModal');
    if (event.target === loginM) loginM.style.display = 'none';
    if (event.target === signupM) signupM.style.display = 'none';
});