(function () {
    "use strict";

    function initFaqAccordion(accordion) {
        var questions = accordion.querySelectorAll(".cmp-faqaccordion__question");
        var expandAllBtn = accordion.querySelector(".cmp-faqaccordion__expand-all");
        var collapseAllBtn = accordion.querySelector(".cmp-faqaccordion__collapse-all");

        function toggleItem(button) {
            var expanded = button.getAttribute("aria-expanded") === "true";
            var answer = button.nextElementSibling;
            button.setAttribute("aria-expanded", String(!expanded));
            if (expanded) {
                answer.setAttribute("hidden", "");
            } else {
                answer.removeAttribute("hidden");
            }
        }

        function expandAll() {
            questions.forEach(function (btn) {
                btn.setAttribute("aria-expanded", "true");
                btn.nextElementSibling.removeAttribute("hidden");
            });
        }

        function collapseAll() {
            questions.forEach(function (btn) {
                btn.setAttribute("aria-expanded", "false");
                btn.nextElementSibling.setAttribute("hidden", "");
            });
        }

        questions.forEach(function (btn) {
            btn.addEventListener("click", function () {
                toggleItem(btn);
            });
        });

        if (expandAllBtn) {
            expandAllBtn.addEventListener("click", expandAll);
        }
        if (collapseAllBtn) {
            collapseAllBtn.addEventListener("click", collapseAll);
        }
    }

    function onDocumentReady() {
        var accordions = document.querySelectorAll('[data-cmp-is="faqaccordion"]');
        accordions.forEach(function (accordion) {
            initFaqAccordion(accordion);
        });
    }

    if (document.readyState !== "loading") {
        onDocumentReady();
    } else {
        document.addEventListener("DOMContentLoaded", onDocumentReady);
    }
})();
