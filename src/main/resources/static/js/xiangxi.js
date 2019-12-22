$(function () {
    findGongSi();
    fillTable();
    $('#datetimepicker2').datetimepicker({
        format: 'YYYY-MM-DD HH:mm',
        locale: moment.locale('zh-cn')
    });
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
                $("#parentId").val(data[i].gongsi_id);
                $("#parentId1").val(data[i].gongsi_id);
                var gongsi_id = data[i].gongsi_id;
                if ("-" == gongsi_id) {
                    oTable.append("<tr class='" + getFlag(i) + "'><td>错误数据，需返回后重新载入</td></tr>");
                    return;
                }

                if ("-" != data[i].time) {//当没有数据时不进行渲染,为了获取父id仍要进行填充数据
                    oTable.append("<tr class='" + getFlag(i) + "'><td>" + data[i].time + "</td><td>" + data[i].tougu + "</td></td><td>" + render(data[i].id) + "</td></tr>");
                }
            }
        }
    })
}

function render(id) {
    var str = "<a href=\"javascript:applyXiangXi('" + id + "')\" style='margin-right: 8px;'>修改 </a>";
    str = str + "<a href=\"javascript:deleteXiangXi('" + id + "')\" style='color: red;'>删除 </a>";
    return str;
}


function applyXiangXi(id) {
    if (typeof id == "undefined" || id == null || id == "") {
            $('#myModal').modal('show');
            //添加
            $('#myModalLabel').html("添加详细");
            $("#tougu").val("");
            $("#id").val("");
            $("#time").val("");
    } else {
        //修改
        $('#myModal').modal('show');
        var url = "http://127.0.0.1:8080/findXX?id=" + id;
        $.ajax({
            url: url,
            type: 'get',
            data: {},
            success: function (data) {
                $('#myModalLabel').html("修改详细");
                $("#time").val(data.time);
                $("#tougu").val(data.tougu);
                $("#id").val(data.id);
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

/**
 * 查询标题的公司信息
 */
function findGongSi() {
    var url = "http://127.0.0.1:8080/findGP";
    $.ajax({
        url: url,
        type: 'get',
        data: {},
        success: function (data) {
            $("#gongsi_code").empty();
            $("#gongsi_name").empty();
            $("#gongsi_code").append(data.id);
            $("#gongsi_name").append(data.name);
            return data;
        }
    })
}

