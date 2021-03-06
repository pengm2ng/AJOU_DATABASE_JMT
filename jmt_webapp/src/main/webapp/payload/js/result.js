window.onload = initialFunction;
const urlParams = new URLSearchParams(window.location.search);

function getParam(paramName) {
    return urlParams.get(paramName);
}

function loadPlace(placeHTML) {
    var deptDiv = getParam("deptDiv");
    var govofcDiv = getParam("govofcDiv");
    var hgdeptDiv = getParam("hgdeptDiv");
    var dept = getParam("dept");
    var startDate = getParam("startDate");
    var endDate = getParam("endDate");

    startDate = (startDate == null) ? "" : startDate;
    endDate = (endDate == null) ? "" : endDate;

    let titleText;
    if (dept.length != 0) {
        titleText = dept;
    } else if (hgdeptDiv.length != 0) {
        titleText = hgdeptDiv;
    } else if (govofcDiv.length != 0) {
        titleText = govofcDiv;
    } else if (deptDiv != 0) {
        titleText = deptDiv;
    } else {
        titleText = "전체";
    }
    document.getElementById("recommendationTitle").textContent = titleText + " 맛집 베스트 TOP 10";

    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (this.readyState === 4 && this.status === 200) {
            var array = JSON.parse(xhr.responseText)["restaurant"];
            if (array.length == 0 || array == null) {
                let recommendationPanel = document.getElementById("recommendationPanel");
                recommendationPanel.textContent = "검색된 결과가 없습니다!";
                recommendationPanel.style.textAlign = "center";
                recommendationPanel.style.color = "rgb(0, 117, 226)";
                recommendationPanel.style.fontWeight = "bold";
            } else {
                array.forEach(restaurant => {
                    let placeElement = document.createElement("div");
                    placeElement.innerHTML = placeHTML;
                    placeElement.getElementsByClassName("placeName")[0].textContent = restaurant["placeName"];
                    placeElement.getElementsByClassName("placeLocation")[0].textContent = "주소  "+restaurant["address"];
                    placeElement.getElementsByClassName("totalAmt")[0].textContent = "총 지출 금액  "+restaurant["totalAmount"];
                    if (jmtCookieIncludes(restaurant["bizNumber"])) {
                        placeElement.getElementsByClassName("likeCount")[0].textContent = "추천수  "+restaurant["likeCount"]+"👍 💗"
                    } else {
                        placeElement.getElementsByClassName("likeCount")[0].textContent = "추천수  "+restaurant["likeCount"]+"👍";
                    }
                    placeElement.getElementsByClassName("placeCategory")[0].textContent = restaurant["category"];
                    placeElement.getElementsByClassName("likePlaceButton")[0].setAttribute("bizNo", restaurant["bizNumber"]);
                    placeElement.getElementsByClassName("likePlaceButton")[0].onclick = function (ev) {

                        var bizNo = ev.target.getAttribute("bizNo");
                        if (jmtCookieIncludes(bizNo)) {
                            // 이미 추천 함
                            document.getElementById("alreadyRecommendedModal").style.display = "block";
                        } else {
                            var xhrI = new XMLHttpRequest();
                            xhrI.onload = function () {
                                if (this.readyState === 4 && this.status === 200) {
                                    pushToJMT(bizNo);
                                    // 추천완료 모달창
                                    document.getElementById("recommendSuccessModal").style.display = "block";
                                    var likeCount = placeElement.getElementsByClassName("likeCount")[0];
                                    likeCount.textContent = "추천수  " + (parseInt(restaurant["likeCount"])+1) + "👍 💗";
                                }
                            };
                            xhrI.open("GET", "http://lanihome.iptime.org:8080/restful/set/recommendation?bizNo="+bizNo, true);
                            xhrI.send();
                        }
                    };  // TODO
                    document.getElementById("recommendationPanel").appendChild(placeElement);
                });
            }
        }
    };
    xhr.open("GET", "http://lanihome.iptime.org:8080/restful/get/top10?deptDiv="
                        +deptDiv+"&govofcDiv="+govofcDiv+"&hgdeptDiv="+hgdeptDiv
                        +"&dept="+dept+"&startDate="+startDate+"&endDate="+endDate, true);
    xhr.send();
}

function loadPlaceHTML(resolve, reject) {
    var xhrElement = new XMLHttpRequest();
    xhrElement.onload = function () {
        if (this.readyState === 4 && this.status === 200) {
            resolve(xhrElement.responseText);
        }
    };
    xhrElement.open("GET", "http://lanihome.iptime.org:8080/payload/html/recommendationElement.html", true);
    xhrElement.send();
}

function initModalButton() {
    document.getElementById("alreadyRecommendedClose").onclick = function () {
        document.getElementById("alreadyRecommendedModal").style.display = "none";
    }
    document.getElementById("recommendSuccessClose").onclick = function () {
        document.getElementById("recommendSuccessModal").style.display = "none";
    }
}

function jmtCookieIncludes(cookieKey) {
    var cookieStr = document.cookie.match(new RegExp(
        "(?:^|; )" + "JMTRecom".replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    cookieStr = cookieStr ? decodeURIComponent(cookieStr[1]) : undefined;
    var cookieJSON;
    if (cookieStr === undefined) {
        return false;
    } else {
        cookieJSON = JSON.parse(cookieStr);
    }
    return cookieJSON["bizNo"].includes(cookieKey);
}

function pushToJMT(key) {
    var cookieStr = document.cookie.match(new RegExp(
        "(?:^|; )" + "JMTRecom".replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    cookieStr = cookieStr ? decodeURIComponent(cookieStr[1]) : undefined;
    var cookieJSON;
    if (cookieStr === undefined) {
        cookieJSON = JSON.parse("{\"bizNo\": []}");
    } else {
        cookieJSON = JSON.parse(cookieStr);
    }
    cookieJSON["bizNo"].push(key);
    document.cookie = "JMTRecom="+JSON.stringify(cookieJSON);
}

/**
 * JMT 쿠키 제거
 */
function removeCookie() {
    document.cookie = "JMTRecom = {\"bizNo\": []}";
}

function initialFunction() {
    new Promise(loadPlaceHTML).then(loadPlace);
    initModalButton();
}
