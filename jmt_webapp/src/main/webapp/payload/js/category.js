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

    xhr.open("GET", "http://lanihome.iptime.org:8080/restful/get/category/sept?deptDiv", true);
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

function loadGovofcDiv(deptDiv) {
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

    xhr.open("GET", "http://lanihome.iptime.org:8080/restful/get/category?deptDiv="+deptDiv, true);
    xhr.send();
}

function initialFunction() {
    loadDeptDiv();
    initIntervalButton();
}


