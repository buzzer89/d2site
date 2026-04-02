(function() {
    "use strict";

    var selectors = {
        self:        '[data-cmp-is="faqaccordion"]',
        item:        ".cmp-faqaccordion__item",
        question:    ".cmp-faqaccordion__question",
        answer:      ".cmp-faqaccordion__answer",
        expandAll:   ".cmp-faqaccordion__expand-all",
        collapseAll: ".cmp-faqaccordion__collapse-all"
    };

    var cssClasses = {
        questionExpanded: "cmp-faqaccordion__question--expanded",
        answerHidden:     "cmp-faqaccordion__answer--hidden"
    };

    function FaqAccordion(config) {

        function init(config) {
            config.element.removeAttribute("data-cmp-is");

            var expandAllBtn = config.element.querySelector(selectors.expandAll);
            var collapseAllBtn = config.element.querySelector(selectors.collapseAll);
            var questions = config.element.querySelectorAll(selectors.question);

            questions.forEach(function(btn) {
                btn.addEventListener("click", function() {
                    toggleItem(btn);
                });
            });

            if (expandAllBtn) {
                expandAllBtn.addEventListener("click", function() {
                    expandAll(config.element);
                });
            }

            if (collapseAllBtn) {
                collapseAllBtn.addEventListener("click", function() {
                    collapseAll(config.element);
                });
            }
        }

        function toggleItem(btn) {
            var item = btn.closest(selectors.item);
            if (!item) return;
            var answer = item.querySelector(selectors.answer);
            if (!answer) return;

            var isExpanded = btn.getAttribute("aria-expanded") === "true";

            if (isExpanded) {
                btn.setAttribute("aria-expanded", "false");
                btn.classList.remove(cssClasses.questionExpanded);
                answer.classList.add(cssClasses.answerHidden);
            } else {
                btn.setAttribute("aria-expanded", "true");
                btn.classList.add(cssClasses.questionExpanded);
                answer.classList.remove(cssClasses.answerHidden);
            }
        }

        function expandAll(el) {
            var items = el.querySelectorAll(selectors.item);
            items.forEach(function(item) {
                var btn = item.querySelector(selectors.question);
                var answer = item.querySelector(selectors.answer);
                if (btn && answer) {
                    btn.setAttribute("aria-expanded", "true");
                    btn.classList.add(cssClasses.questionExpanded);
                    answer.classList.remove(cssClasses.answerHidden);
                }
            });
        }

        function collapseAll(el) {
            var items = el.querySelectorAll(selectors.item);
            items.forEach(function(item) {
                var btn = item.querySelector(selectors.question);
                var answer = item.querySelector(selectors.answer);
                if (btn && answer) {
                    btn.setAttribute("aria-expanded", "false");
                    btn.classList.remove(cssClasses.questionExpanded);
                    answer.classList.add(cssClasses.answerHidden);
                }
            });
        }

        if (config && config.element) {
            init(config);
        }
    }

    function onDocumentReady() {
        var elements = document.querySelectorAll(selectors.self);
        for (var i = 0; i < elements.length; i++) {
            new FaqAccordion({ element: elements[i] });
        }

        var observer = new MutationObserver(function(mutations) {
            mutations.forEach(function(mutation) {
                mutation.addedNodes.forEach(function(node) {
                    if (node.querySelectorAll) {
                        var newElements = node.querySelectorAll(selectors.self);
                        for (var i = 0; i < newElements.length; i++) {
                            new FaqAccordion({ element: newElements[i] });
                        }
                    }
                });
            });
        });

        observer.observe(document.body, { subtree: true, childList: true });
    }

    if (document.readyState !== "loading") {
        onDocumentReady();
    } else {
        document.addEventListener("DOMContentLoaded", onDocumentReady);
    }
})();
