$(document).ready(function () {
    fillTable();
});
/**
 * 填充表格
 */
function fillTable() {
    var oTable = $("#tb1");
    oTable.empty();
    var parentCode = $("#queryCode").val()
    var queryUrl = "http://127.0.0.1:8080/queryXX";
    $.ajax({
        url: queryUrl,
        type: 'get',
        data: {},
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                oTable.append("<tr class='" + getFlag(i) + "'><td>" + data[i].time + "</td><td>" + data[i].tougu + "</td></td><td>" + render(data[i].id) + "</td></tr>");
            }
        }
    })
}

function render(id) {
    var str = "<a href=\"javascript:applyXiangXi('" + id + "')\">修改 </a>";
    str = str + "<a href=\"javascript:deleteXiangXi('" + id + "')\">删除 </a>";
    return str;
}


function applyXiangXi(id) {
    $('#myModal').modal('show');
    if (typeof id == "undefined" || id == null || id == "") {
        //添加
        $('#myModalLabel').html("添加详细");
        $("#code").val("");
        $("#code").attr("disabled", false);
        $("#name").val("");
    } else {
        //修改
        var url = "http://127.0.0.1:8080/findXX?id=" + id;
        $.ajax({
            url: url,
            type: 'get',
            data: {},
            success: function (data) {
                $('#myModalLabel').html("修改详细");
                $("#code").val(data.id);
                $("#name").val(data.name);
                $("#code").attr("disabled", true);
            }
        })
    }
}



/**
 * 构造表格行的样式
 * @param i
 * @returns {number}
 */
function getFlag(i) {
    var flag = i % 4;
    if (flag == 0) {
        flag = "active"
    } else if (flag == 1) {
        flag = "success"
    } else if (flag == 2) {
        flag = "warning"
    } else if (flag == 3) {
        flag = "danger"
    }
    return flag;
}
