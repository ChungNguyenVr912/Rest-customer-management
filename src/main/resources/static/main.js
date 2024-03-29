$(function () {
    $('#customer-form').submit(function (event) {
        event.preventDefault();
        var name = $('#name').val();
        var email = $('#email').val();
        var address = $('#address').val();
        var avatar = document.getElementById('avatar').files[0];
        const form = new FormData();
        form.append("name", name);
        form.append("email", email);
        form.append("address", address);
        form.append("avatar", avatar);
        console.log(form);

        $.ajax({
            url: '/api/customers',
            type: 'POST',
            data: form,
            processData: false,
            contentType: false,
            success: function(response,status,jqXHR) {
                console.log(jqXHR+' : '+status);
                console.log(response);
                this.reset();
            },
            error: function(xhr, status, error) {
                console.log(error.status + ':' + status);
            }
        });
    });
    loadList();
});


function loadList() {
    $.getJSON(
        'api/customers',
        function (response) {
            const list = $('#listCustomer');
            list.html(
                `<div class="d-flex border py-1 px-2">
                    <div class="col fw-bold fs-4">Name</div>
                    <div class="col-3 fw-bold fs-4">Email</div>
                    <div class="col-3 fw-bold fs-4">Address</div>
                    <div class="col-2 fw-bold fs-4">Avatar</div>
                    <div class="fs-4 fw-bold">Edit</div>
                    <div class="fw-bold fs-4">Delete</div>
                </div>`
            );
            response.forEach(function (customer) {
                list.append(
                    `<div class="d-flex border py-1 px-2">
                        <div class="col">${customer.name}</div>
                        <div class="col-3">${customer.email}</div>
                        <div class="col-3">${customer.address}</div>
                        <div class="col-2 overflow-hidden text-nowrap me-1"><a href="${customer.avatar}">Avatar url</a></div>
                        <div><a href="/edit/${customer.id}">
                            <button class="btn btn-warning">Edit</button>
                        </a>
                        </div>
                        <div>
                            <button class="btn btn-danger"
                                    onClick="deleteCustomer(${customer.id})">Delete
                            </button>
                        </div>
                    </div>`
                )
            });
        }
    )
}

function deleteCustomer(id) {
    $.ajax(
        {
            url: 'api/customers/' + id,
            type: 'delete',
            success: function (result, status, jqXHR) {
                alert("Status:" + jqXHR.status + ":" + status)
                setTimeout(function () {
                    loadList();
                }, 500)
            }
            ,
            error: function (result, status, error) {
                alert("Error: " + status + error);
                console.log("Loi: " + error);
            }
        }
    );
}