(function () {
    "use strict";

    function initCarousel(component) {
        var slides = component.querySelectorAll(".cmp-testimonialcarousel__slide");
        var prevBtn = component.querySelector(".cmp-testimonialcarousel__prev");
        var nextBtn = component.querySelector(".cmp-testimonialcarousel__next");
        var activeIndex = 0;

        if (slides.length <= 1) {
            return;
        }

        function showSlide(index) {
            slides[activeIndex].classList.remove("cmp-testimonialcarousel__slide--active");
            activeIndex = (index + slides.length) % slides.length;
            slides[activeIndex].classList.add("cmp-testimonialcarousel__slide--active");
        }

        if (prevBtn) {
            prevBtn.addEventListener("click", function () {
                showSlide(activeIndex - 1);
            });
        }

        if (nextBtn) {
            nextBtn.addEventListener("click", function () {
                showSlide(activeIndex + 1);
            });
        }
    }

    function onReady() {
        var components = document.querySelectorAll('[data-cmp-is="testimonialcarousel"]');
        for (var i = 0; i < components.length; i++) {
            initCarousel(components[i]);
        }
    }

    if (document.readyState !== "loading") {
        onReady();
    } else {
        document.addEventListener("DOMContentLoaded", onReady);
    }
})();
