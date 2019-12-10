$(document).ready(function () {
    fillTable();
});

function fillTable() {
    var oTable = $("#tb1");
    oTable.empty();
    var queryCode=$("#queryCode").val()
    var queryName = $("#queryName").val()
    var queryUrl = "http://127.0.0.1:8080/queryGP?queryCode=" + queryCode + "&queryName=" + queryName;
    $.ajax({
        url: queryUrl,
        type: 'get',
        data: {},
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                var id = data[i].id;
                var name = data[i].name;
                var flag = getFlag(i);
                oTable.append("<tr class='" + flag + "'><td>" + id + "</td><td>" + name + "</td></td><td>" + render(id) + "</td></tr>");
            }
        }
    })
}

function render(id) {
    var str = "<a href=\"javascript:findGongSi('" + id + "')\">详细 </a>";
    str = str + "<a href=\"javascript:modifyGongSi('" + id + "')\">修改 </a>";
    str = str + "<a href=\"javascript:deleteGongSi('" + id + "')\">删除 </a>";
    return str;
}

//构造表格行的样式
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
    //模态框-确认按钮
    $("#addCommit").click(function () {
        addGongSi()
        $('#myModal').modal('hide')
    });
})

function addGongSi() {
    var id = $("#code").val();
    var name = $("#name").val();
}

function findGongSi(id) {
    alert(id)
}

function modifyGongSi(id) {
    $('#myModal').modal('show');
    if(typeof id == "undefined" || id == null || id == ""){
        //添加
        $('#myModalLabel').html("添加公司");
        $("#code").val("");
        $("#code").attr("disabled", false);
        $("#name").val("");
    }else {
        //修改
        $('#myModalLabel').html("修改公司");
        $("#code").val("123");
        $("#code").attr("disabled", true);
        $("#name").val("456");
    }
}

function deleteGongSi(id) {
    Ewin.confirm({ message: "确认删除" }).on(function (e) {
        if (!e) {
            return;
        }
        var url ="http://127.0.0.1:8080/deleteGP?id="+id;
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

function queryTable() {
    fillTable()
}