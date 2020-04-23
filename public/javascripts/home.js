function getEmployees() {
    $.ajax({
        url: "/api/v1/employees",
        data: '',
        headers: {
            'X-Auth-Token': localStorage.getItem('token')
        },
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (res) {
            var employees = res.employees;
            var tag = $("#employees");
            var empdata = '';
            $.each(employees, function (k, emp) {
                empdata += '<tr>' +
                    '<td>' + emp.firstName + ' ' + emp.lastName + '</td>' +
                    '<td>' + emp.email + '</td>' +
                    '<td>' + emp.mobile + '</td>' +
                    '<td>' +
                    '<a href="#editEmployeeModal" data-id="' + emp.id + '" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">Edit</i></a> | ' +
                    '<a href="#deleteEmployeeModal" data-id="' + emp.id + '" class="delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Delete">Delete</i></a>' +
                    '</td>' +
                    '</tr>';
            });
            if (empdata)
                tag.html(empdata);
            else
                tag.html('<tr><td colspan="4">No data</td></tr>');
        }
    });
}
getEmployees();
var empid = '';
$(document).on('click', ".edit", function () {
    $('.emailerror').html('');
    empid = $(this).data("id");
    $.ajax({
        url: "/api/v1/employees/" + empid,
        data: '',
        headers: {
            'X-Auth-Token': localStorage.getItem('token')
        },
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (res) {
            var emp = res.employee;
            $("#firstNameEdit").val(emp.firstName);
            $("#lastNameEdit").val(emp.lastName);
            $("#emailEdit").val(emp.email);
            $("#phoneEdit").val(emp.mobile);
        }
    });
});

$("#save").on('click', function () {
    $.ajax({
        url: "/api/v1/employees/" + empid,
        data: {
            firstName: $("#firstNameEdit").val(),
            lastName: $("#lastNameEdit").val(),
            email: $("#emailEdit").val(),
            mobile: $("#phoneEdit").val()
        },
        headers: {
            'X-Auth-Token': localStorage.getItem('token')
        },
        type: "PATCH",
        success: function (res) {
            $("#editEmployeeModal").modal("hide");
            getEmployees();
        }
    });
});

$(document).on('click', ".addEmployeeModal", function () {
    $('.emailerror').html('');
});

$("#add").on('click', function () {
    $.ajax({
        url: "/api/v1/employees",
        data: {
            firstName: $("#firstName").val(),
            lastName: $("#lastName").val(),
            email: $("#email").val(),
            mobile: $("#phone").val()
        },
        headers: {
            'X-Auth-Token': localStorage.getItem('token')
        },
        type: "POST",
        success: function (res) {
            $("#addEmployeeModal").modal("hide");
            getEmployees();
        },
        error: function (res) {
            var err = res.responseJSON;
            console.log(err);
            
            if (err.email) {              
                var empdata = '';
                $.each(err.email, function (k, emp) {
                    empdata+= "<li>"+emp+"</li>";
                });
                $('.emailerror').html(empdata);
            }

        }
    });
});

var empid = '';
$(document).on('click', ".delete", function () {
    empid = $(this).data("id");
});

$("#delete").on('click', function () {
    $.ajax({
        url: "/api/v1/employees/" + empid,
        data: '',
        headers: {
            'X-Auth-Token': localStorage.getItem('token')
        },
        type: "DELETE",
        dataType: "json",
        contentType: "application/json",
        success: function (res) {
            getEmployees();
            $("#deleteEmployeeModal").modal("hide");
        }
    });
});

$("#logout").on('click', function () {
    localStorage.clear('token', 'expiresOn');
    window.location = "/";
});