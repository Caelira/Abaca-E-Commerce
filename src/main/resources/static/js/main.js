// Initialization
document.addEventListener("DOMContentLoaded", function() {
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

    // Global URL Parameter Watcher for Notifications
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('registered')) {
        showNotification('Account created successfully! Please log in.', 'success');
    }
    if (urlParams.has('error')) {
        showNotification('Operation failed. Please check your inputs.', 'error');
    }
});

// Global Notification Function
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

    const icon = type === 'success' ? '✅' : '❌';

    toast.innerHTML = `
        <div class="toast-icon">${icon}</div>
        <div class="toast-message">${message}</div>
    `;

    container.appendChild(toast);

    // Animate in
    setTimeout(() => toast.classList.add('show'), 10);

    // Animate out
    setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => toast.remove(), 400);
    }, 3500);
}

// Modal Functions
function openModal(formId) {
    const modal = document.getElementById('authModal');
    if (modal) {
        modal.style.display = 'flex';
        toggleAuth(formId);
    }
}

function closeModal() {
    const modal = document.getElementById('authModal');
    if (modal) {
        modal.style.display = 'none';
    }
}

function toggleAuth(formId) {
    document.getElementById('loginForm').style.display = 'none';
    document.getElementById('signupForm').style.display = 'none';
    document.getElementById(formId).style.display = 'block';
}