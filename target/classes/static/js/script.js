function addEmployee() {

}

function editEmployee(button) {
    let employeeId = button.getAttribute('data-id');

    fetch('/api/employees/' + employeeId)
        .then(response => response.json())
        .then(data => {

        })
        .catch(error => console.error('Ошибка:', error));

}

function deleteEmployee(button) {
    let employeeId = button.getAttribute('data-id');
    if (confirm('Вы уверены, что хотите удалить сотрудника?')) {
        fetch('/api/employees/' + employeeId, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    alert('Сотрудник удален');
                    location.reload();
                } else {
                    alert('Ошибка при удалении сотрудника');
                }
            })
            .catch(error => console.error('Ошибка:', error));
    }
}

function submitForm(event) {
    event.preventDefault();
    let form = event.target;
    let url = form.action;
    let formData = new FormData(form);
    fetch(url, {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                alert('Данные успешно сохранены');
                location.reload();
            } else {
                alert('Ошибка при сохранении данных');
            }
        })
        .catch(error => console.error('Ошибка:', error));
}
