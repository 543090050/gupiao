$(document).ready(function () {
    fillTable();
});

function fillTable() {
    var oTable = $("#tb1");
    // 清空表格
    oTable.empty();
    var queryUrl = "http://127.0.0.1:8080/queryGP";
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
}

function deleteGongSi(id) {

}