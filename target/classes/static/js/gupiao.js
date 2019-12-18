$(document).ready(function () {
    fillTable();
});

//回车搜索
document.onkeydown = function (e) {
    var theEvent = window.event || e;
    var code = theEvent.keyCode || theEvent.which;
    if (code == 13) {
        $("#queryButton").click();
    }
}

/**
 * 填充表格
 */
function fillTable() {
    var oTable = $("#tb1");
    oTable.empty();
    var queryCode = $("#queryCode").val()
    var queryName = $("#queryName").val()
    var queryUrl = "http://127.0.0.1:8080/queryGP?queryCode=" + queryCode + "&queryName=" + queryName;
    $.ajax({
        url: queryUrl,
        type: 'get',
        data: {},
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                oTable.append("<tr class='" + getFlag(i) + "'><td>" + data[i].id + "</td><td>" + data[i].name + "</td></td><td>" + render(data[i].id) + "</td></tr>");
            }
        }
    })
}

function render(id) {
    // var str = "<a href=\"xiangxi/index?gpid=" + id + "\" target=\"_blank\">详细 </a>";//打开新窗口
    var str = "<a href=\"xiangxi/index?gpid=" + id + "\" style='margin-right: 8px;'>详细 </a>";
    str = str + "<a href=\"javascript:applyGongSi('" + id + "')\" style='margin-right: 8px;'>修改 </a>";
    str = str + "<a href=\"javascript:deleteGongSi('" + id + "')\" style='color: red;'>删除 </a>";
    return str;
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

$(function () {
    //模态框-确认按钮-隐藏窗口
    $("#addCommit").click(function () {
        $('#myModal').modal('hide')
    });
})

function applyGongSi(id) {
    $('#myModal').modal('show');
    if (typeof id == "undefined" || id == null || id == "") {
        //添加
        $('#myModalLabel').html("添加公司");
        $("#code").val("");
        $("#code").attr("readonly", false);
        $("#name").val("");
    } else {
        //修改
        var url = "http://127.0.0.1:8080/findGP?id=" + id;
        $.ajax({
            url: url,
            type: 'get',
            data: {},
            success: function (data) {
                $('#myModalLabel').html("修改公司");
                $("#code").val(data.id);
                $("#name").val(data.name);
                $("#code").attr("readonly", true);
            }
        })
    }
}

function deleteGongSi(id) {
    Ewin.confirm({message: "确认删除"}).on(function (e) {
        if (!e) {
            return;
        }
        var url = "http://127.0.0.1:8080/deleteGP?id=" + id;
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