window.onload = initialFunction;


function loadDeptDiv() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (this.readyState === 4 && this.status === 200) {
            var jsonList = JSON.parse(xhr.responseText)["deptDiv"];
            jsonList.forEach(element => {
                var temp = document.createElement("option");
                temp.textContent = element;
                document.getElementsByName("deptDivSelector")[0].appendChild(temp);
            });
        }
    };

    xhr.open("GET", "http://lanihome.iptime.org:8080/restful/get/category", true);
    xhr.send();
}

function initIntervalButton() {
    $("input[name='setIntervalSelection']:radio").change(function () {
        var value = this.value;

        if (value === "checkInterval") {
            var xhr = new XMLHttpRequest();
            xhr.onload = function () {
                if (this.readyState === 4 && this.status === 200) {
                    document.getElementById("intervalSelectPanel").innerHTML = xhr.responseText;
                }
            };
            xhr.open("GET", "http://lanihome.iptime.org:8080/category_interval.html", true);
            xhr.send();
        } else {
            document.getElementById("intervalSelectPanel").innerHTML = "";
        }
    });
}

function initSubmitButton() {
    $("#submitButton").click(function (ev) {
        var intervalQuery = "";
        var isIllegalArgument = false;
        if (document.querySelector('input[name="setIntervalSelection"]:checked').value === "checkInterval") {
            var fromYear = document.getElementsByName("yearFromSelector")[0].selectedOptions[0].value;
            var fromMonth = document.getElementsByName("monthFromSelector")[0].selectedOptions[0].value;

            var toYear = document.getElementsByName("yearToSelector")[0].selectedOptions[0].value;
            var toMonth = document.getElementsByName("monthToSelector")[0].selectedOptions[0].value;

            if (new Date(fromYear+"-"+fromMonth) > new Date(toYear+"-"+toMonth)) {
                alert("검색기간이 잘못되었습니다! 다시 입력해 주세요");
                isIllegalArgument = true;
            }
            intervalQuery += "&startDate=" + fromYear + "-" + fromMonth + "&endDate" + toYear + "-" + toMonth;
        }

        if (!isIllegalArgument) {
            var selectedDeptDiv = document.getElementsByName("deptDivSelector")[0].selectedOptions[0].value;
            var selectedGovofcDiv = document.getElementsByName("govofcDivSelector")[0].selectedOptions[0].value;
            var selectedHgdeptDiv = document.getElementsByName("hgdeptDivSelector")[0].selectedOptions[0].value;
            var selectedDept = document.getElementsByName("deptSelector")[0].selectedOptions[0].value;
            selectedDeptDiv = selectedDeptDiv == "전체" ? null : selectedDeptDiv;
            selectedGovofcDiv = selectedGovofcDiv == "전체" ? null : selectedGovofcDiv;
            selectedHgdeptDiv = selectedHgdeptDiv == "전체" ? null : selectedHgdeptDiv;
            selectedDept = selectedDept == "전체" ? null : selectedDept;
    
            var hrefLink = "result.html?deptDiv="+selectedDeptDiv+"&govofcDiv="+selectedGovofcDiv+"&hgdeptDiv="+selectedHgdeptDiv+"&dept="+selectedDept;
            location.href = hrefLink + intervalQuery;
        }
    });
}

function initDeptDivSelection() {
    $("select[name='deptDivSelector']").change(function () {
        var selectedDeptDiv = document.getElementsByName("deptDivSelector")[0].selectedOptions[0].value;
        selectedDeptDiv = selectedDeptDiv == "전체" ? null : selectedDeptDiv;
        document.getElementsByName("govofcDivSelector")[0].innerHTML = "<option>전체</option>";
        var xhr = new XMLHttpRequest();
        xhr.onload = function () {
            if (this.readyState === 4 && this.status === 200) {
                var jsonList = JSON.parse(xhr.responseText)["govofcDiv"];
                jsonList.forEach(element => {
                    var temp = document.createElement("option");
                    temp.textContent = element;
                    document.getElementsByName("govofcDivSelector")[0].appendChild(temp);
                });
            }
        };
        xhr.open("GET", "http://lanihome.iptime.org:8080/restful/get/category?deptDiv="+selectedDeptDiv, true);
        xhr.send();
    });
}

function initGovofcDivSelection() {
    $("select[name='govofcDivSelector']").change(function () {
        var selectedDeptDiv = document.getElementsByName("deptDivSelector")[0].selectedOptions[0].value;
        var selectedGovofcDiv = document.getElementsByName("govofcDivSelector")[0].selectedOptions[0].value;
        document.getElementsByName("hgdeptDivSelector")[0].innerHTML = "<option>전체</option>";
        selectedGovofcDiv = selectedGovofcDiv == "전체" ? null : selectedGovofcDiv;
        var xhr = new XMLHttpRequest();
        xhr.onload = function () {
            if (this.readyState === 4 && this.status === 200) {
                var jsonList = JSON.parse(xhr.responseText)["hgdeptDiv"];
                jsonList.forEach(element => {
                    var temp = document.createElement("option");
                    temp.textContent = element;
                    document.getElementsByName("hgdeptDivSelector")[0].appendChild(temp);
                });
            }
        };
        xhr.open("GET", "http://lanihome.iptime.org:8080/restful/get/category?deptDiv="+selectedDeptDiv+"&govofcDiv="+selectedGovofcDiv, true);
        xhr.send();
    });
}

function initHgdeptDivSelection() {
    $("select[name='hgdeptDivSelector']").change(function () {
        var selectedDeptDiv = document.getElementsByName("deptDivSelector")[0].selectedOptions[0].value;
        var selectedGovofcDiv = document.getElementsByName("govofcDivSelector")[0].selectedOptions[0].value;
        var selectedHgdeptDiv = document.getElementsByName("hgdeptDivSelector")[0].selectedOptions[0].value;
        document.getElementsByName("deptSelector")[0].innerHTML = "<option>전체</option>";
        selectedHgdeptDiv = selectedHgdeptDiv == "전체" ? null : selectedHgdeptDiv;
        var xhr = new XMLHttpRequest();
        xhr.onload = function () {
            if (this.readyState === 4 && this.status === 200) {
                var jsonList = JSON.parse(xhr.responseText)["dept"];
                jsonList.forEach(element => {
                    var temp = document.createElement("option");
                    temp.textContent = element;
                    document.getElementsByName("deptSelector")[0].appendChild(temp);
                });
            }
        };
        xhr.open("GET", "http://lanihome.iptime.org:8080/restful/get/category?deptDiv="
                                +selectedDeptDiv+"&hgdeptDiv="+selectedHgdeptDiv+"&govofcDiv="+selectedGovofcDiv, true);
        xhr.send();
    });
}

function initialFunction() {
    loadDeptDiv();
    initIntervalButton();
    initSubmitButton();
    initDeptDivSelection();
    initGovofcDivSelection();
    initHgdeptDivSelection();
}
