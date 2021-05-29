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
            if (array.length == 0) {
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
                    placeElement.getElementsByClassName("likeCount")[0].textContent = "추천수  "+restaurant["likeCount"]+"👍";
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

function initialFunction() {
    new Promise(loadPlaceHTML).then(loadPlace);
}
