/**
 * 一个面板插件
 */
(function () {
    /**
     * logEl 输出的容器element
     * isInitialized 是否初始化
     */
    let logEl, isInitialized = false;

    /**
     * 创建元素
     * tag 标签名称
     * css 样式字符串
     */
    function createElement(tag, cssText) {
        let element = document.createElement(tag);
        if (cssText == null || cssText === '') {
        } else {
            element.style.cssText = cssText;
        }
        return element;
    }

    /**
     * 将 css Map 转换成 字符串
     */
    function map2String(cssMap) {
        let cssText = '';
        cssMap.forEach(function (value, key,) {
            cssText = cssText + key + ":" + value + ";";
        });
        return cssText;
    }


    /**
     * 生成面板
     */
    function createPanel() {
        let cssMap = new Map();
        cssMap.set('font-family', 'Helvetica,Arial,sans-serif');
        cssMap.set('font-size', '10px');
        cssMap.set('font-weight', 'bold');
        cssMap.set('padding', '5px');
        cssMap.set('text-align', 'left');
        cssMap.set('opacity', '0.8');
        cssMap.set('position', 'fixed');
        cssMap.set('left', '10px');
        cssMap.set('right', '10px');
        cssMap.set('top', '10');
        cssMap.set('min-width', '200px');
        cssMap.set('max-height', '95vh');
        cssMap.set('overflow', 'auto');
        // 颜色设置
        cssMap.set('background', 'black');
        cssMap.set('color', 'lightgreen');
        return createElement('div', map2String(cssMap));
    }

    /**
     * 创建行
     */
    function createLines(content) {
        // ☆★
        let lineHeight = 'line-height:18px;margin:0; white-space: pre-wrap;word-wrap: break-word;';
        let el = createElement('pre', lineHeight);
        el.textContent = "☆" + content;
        logEl.appendChild(el);
        // Scroll to last element
        logEl.scrollTop = logEl.scrollHeight - logEl.clientHeight;
    }


    /**
     * 日志信息，自定义log方法
     */
    function log() {
        // 想要输出的内容
        let val = [].slice.call(arguments).reduce(function (prev, arg) {
            return prev + ' ' + arg;
        }, '');
        createLines(val);
    }

    /**
     * 清空数据
     */
    function clear() {
        logEl.innerHTML = '';
    }

    /**
     * 初始化插件，可以添加附加选项
     */
    function init() {
        if (isInitialized) {
            return;
        }
        isInitialized = true;
        logEl = createPanel();
        document.body.appendChild(logEl);
    }

    /**
     * 销毁插件并恢复原来的控制台显示
     */
    function destroy() {
        isInitialized = false;
        logEl.remove();
    }

    window.logPanel = {
        // 初始化
        init: init,
        // 添加一条信息
        log: log,
        clear: clear,
        destroy: destroy
    };
})();
