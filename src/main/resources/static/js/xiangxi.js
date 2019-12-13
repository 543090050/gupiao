$(document).ready(function () {
    fillTable();
});
/**
 * 填充表格
 */
function fillTable() {
    var oTable = $("#tb1");
    oTable.empty();
    var queryUrl = "http://127.0.0.1:8080/queryXX";
    $.ajax({
        url: queryUrl,
        type: 'get',
        data: {},
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                oTable.append("<tr class='" + getFlag(i) + "'><td>" + data[i].time + "</td><td>" + data[i].tougu + "</td></td><td>" + render(data[i].id) + "</td></tr>");
                $("#parentId").val(data[i].gongsi_id);
                $("#parentId1").val(data[i].gongsi_id);
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
        $("#tougu").val("");
        $("#id").val("");
    } else {
        //修改
        var url = "http://127.0.0.1:8080/findXX?id=" + id;
        $.ajax({
            url: url,
            type: 'get',
            data: {},
            success: function (data) {
                $('#myModalLabel').html("修改详细");
                $("#time").val(data.time);
                $("#tougu").val(data.tougu);
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

function goBack() {
    window.location.href = "http://127.0.0.1:8080/index";
}


function deleteXiangXi(id) {
    Ewin.confirm({message: "确认删除"}).on(function (e) {
        if (!e) {
            return;
        }
        var url = "http://127.0.0.1:8080/deleteXX?id=" + id;
        $.ajax({
            url: url,
            type: 'get',
            data: {},
            success: function (data) {
                fillTable()
            }
        })
    });
}
