window.onload = initialFunction;


function loadDeptDiv() {
    var xhr = new XMLHttpRequest();
    xhr.onreadyStateChange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var jsonList = JSON.parse(xhr.responseText)["deptDiv"];
            jsonList.forEach(element => {
                var temp = document.createElement("option");
                temp.textContent = element;
                document.getElementsByName("deptDivSelector").appendChild(temp);
            });
        }
    };

    xhr.open("GET", "http://lanihome.iptime.org:8080/restful/get/category", true);   // TODO Query 확실치 않음
    xhr.send();
}

function initIntervalButton() {
    $("input[name='setIntervalSelection']:radio").change(function () {
        var value = this.value;

        if (value === "checkInterval") {
            var xhr = new XMLHttpRequest();
            xhr.onreadyStateChange = function () {
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
        var selectedDeptDiv = document.getElementsByName("deptDivSelector")[0].selectedOptions[0].value;
        var selectedGovofcDiv = document.getElementsByName("govofcDivSelector")[0].selectedOptions[0].value;
        var selectedHgdeptDiv = document.getElementsByName("hgdeptDivSelector")[0].selectedOptions[0].value;
        var selectedDept = document.getElementsByName("deptSelector")[0].selectedOptions[0].value;
        selectedDeptDiv = selectedDeptDiv == "전체" ? null : selectedDeptDiv;
        selectedGovofcDiv = selectedGovofcDiv == "전체" ? null : selectedGovofcDiv;
        selectedHgdeptDiv = selectedHgdeptDiv == "전체" ? null : selectedHgdeptDiv;
        selectedDept = selectedDept == "전체" ? null : selectedDept;
        location.href="result.html?deptDiv="+selectedDeptDiv+"&govofcDiv="+selectedGovofcDiv+"&hgdeptDiv="+selectedHgdeptDiv+"&dept="+selectedDept;
    });
}

function initDeptDivSelection() {
    $("select[name='deptDivSelector']").change(function () {
        var selectedDeptDiv = document.getElementsByName("deptDivSelector")[0].selectedOptions[0].value;
        selectedDeptDiv = selectedDeptDiv == "전체" ? null : selectedDeptDiv;
        var xhr = new XMLHttpRequest();
        xhr.onreadyStateChange = function () {
            if (this.readyState === 4 && this.status === 200) {
                var jsonList = JSON.parse(xhr.responseText)["govofcDiv"];
                jsonList.forEach(element => {
                    var temp = document.createElement("option");
                    temp.textContent = element;
                    document.getElementsByName("govofcDivSelector").appendChild(temp);
                });
            }
        };
        xhr.open("GET", "http://lanihome.iptime.org:8080/restful/get/category?deptDiv="+selectedDeptDiv, true);
        xhr.send();
    });
}

function initGovofcDivSelection() {
    $("select[name='deptDivSelector']").change(function () {
        var selectedDeptDiv = document.getElementsByName("deptDivSelector")[0].selectedOptions[0].value;
        var selectedGovofcDiv = document.getElementsByName("govofcDivSelector")[0].selectedOptions[0].value;
        selectedGovofcDiv = selectedGovofcDiv == "전체" ? null : selectedGovofcDiv;
        var xhr = new XMLHttpRequest();
        xhr.onreadyStateChange = function () {
            if (this.readyState === 4 && this.status === 200) {
                var jsonList = JSON.parse(xhr.responseText)["hgdeptDiv"];
                jsonList.forEach(element => {
                    var temp = document.createElement("option");
                    temp.textContent = element;
                    document.getElementsByName("hgdeptDivSelector").appendChild(temp);
                });
            }
        };
        xhr.open("GET", "http://lanihome.iptime.org:8080/restful/get/category?deptDiv="+selectedDeptDiv+"&hgdeptDiv="+selectedGovofcDiv, true);
        xhr.send();
    });
}

function initHgdeptDivSelection() {
    $("select[name='deptDivSelector']").change(function () {
        var selectedDeptDiv = document.getElementsByName("deptDivSelector")[0].selectedOptions[0].value;
        var selectedGovofcDiv = document.getElementsByName("govofcDivSelector")[0].selectedOptions[0].value;
        var selectedHgdeptDiv = document.getElementsByName("hgdeptDivSelector")[0].selectedOptions[0].value;
        selectedHgdeptDiv = selectedHgdeptDiv == "전체" ? null : selectedHgdeptDiv;
        var xhr = new XMLHttpRequest();
        xhr.onreadyStateChange = function () {
            if (this.readyState === 4 && this.status === 200) {
                var jsonList = JSON.parse(xhr.responseText)["dept"];
                jsonList.forEach(element => {
                    var temp = document.createElement("option");
                    temp.textContent = element;
                    document.getElementsByName("deptSelector").appendChild(temp);
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