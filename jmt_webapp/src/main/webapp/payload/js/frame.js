function initSize() {
    window.onresize = function (ev) {
        if (ev.target.screen.availHeight < 1000) {
            document.getElementsByTagName("body")[0].style.height = "" + window.screen.availHeight + "px";
            document.getElementsByClassName("initFrame")[0].style.height = "-webkit-fill-available";
        }
    }
    if (window.screen.availHeight < 1000) {
        document.getElementsByTagName("body")[0].style.height = "" + window.screen.availHeight + "px";
        document.getElementsByClassName("initFrame")[0].style.height = "-webkit-fill-available";
    }
}

$(document).ready(initSize);
