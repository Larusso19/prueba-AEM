(function () {
    'use strict';

    document.querySelectorAll('[data-component="card-list-showcase"]').forEach(function (section) {
        var btn        = section.querySelector('[data-action="load-all"]');
        var overlay    = section.querySelector('.card-list-showcase__modal-overlay');
        var content    = section.querySelector('.card-list-showcase__modal-content');
        var closeBtn   = section.querySelector('.card-list-showcase__modal-close');
        var servletUrl = section.getAttribute('data-servlet-path');

        if (!btn || !servletUrl) return;

        btn.addEventListener('click', function () {
            btn.disabled = true;
            btn.textContent = 'Cargando...';

            fetch(servletUrl)
                .then(function (res) { return res.json(); })
                .then(function (data) {
                    content.innerHTML =
                        '<span class="card-list-showcase__modal-status">' + data.status + '</span>' +
                        '<p class="card-list-showcase__modal-title">' + data.message + '</p>' +
                        '<p>El servlet <strong>holafuturo</strong> respondió correctamente.<br>' +
                        'En producción, este endpoint devolvería las cards desde una fuente de datos externa.</p>';
                    overlay.classList.add('is-open');
                })
                .catch(function () {
                    content.innerHTML = '<p>Error al conectar con el servlet.</p>';
                    overlay.classList.add('is-open');
                })
                .finally(function () {
                    btn.disabled = false;
                    btn.textContent = 'Ver todos';
                });
        });

        closeBtn.addEventListener('click', function () {
            overlay.classList.remove('is-open');
        });

        overlay.addEventListener('click', function (e) {
            if (e.target === overlay) overlay.classList.remove('is-open');
        });
    });
}());