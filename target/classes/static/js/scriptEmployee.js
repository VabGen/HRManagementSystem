// function addEmployee() {
//     const newEmployeeData = collectNewEmployeeData();
//     createEmployeeApiCall(newEmployeeData)
//         .then(() => {
//             alert('Сотрудник успешно добавлен');
//             location.reload();
//         })
//         .catch((error) => {
//             console.error("Ошибка при создании сотрудника:", error);
//         });
// }
//
// function editEmployee(button) {
//     const employeeId = button.getAttribute('data-id');
//
//     fetch('/api/employee/' + employeeId)
//         .then(response => response.json())
//         .then(data => {
//             document.getElementById('employeeNameInput').value = data.name;
//             document.getElementById('employeePositionInput').value = data.position;
//         })
//         .catch(error => console.error('Ошибка при получении данных о сотруднике:', error));
// }
//
// function deleteEmployee(button) {
//     const employeeId = button.getAttribute('data-id');
//     if (confirm('Вы уверены, что хотите удалить сотрудника?')) {
//         fetch('/api/employee/' + employeeId, {
//             method: 'DELETE'
//         })
//             .then(response => {
//                 if (response.ok) {
//                     alert('Сотрудник удален');
//                     location.reload();
//                 } else {
//                     alert('Ошибка при удалении сотрудника');
//                 }
//             })
//             .catch(error => console.error('Ошибка при удалении сотрудника:', error));
//     }
// }
//
// function submitForm(event) {
//     event.preventDefault();
//     const form = event.target;
//     const url = form.action;
//     const formData = new FormData(form);
//     fetch(url, {
//         method: 'POST', body: formData
//     })
//         .then(response => {
//             if (response.ok) {
//                 alert('Данные успешно сохранены');
//                 location.reload();
//             } else {
//                 alert('Ошибка при сохранении данных');
//             }
//         })
//         .catch(error => console.error('Ошибка при сохранении данных:', error));
// }
//
// // form for creating a new employee
// document.getElementById('employeeForm').addEventListener('submit', async (event) => {
//     event.preventDefault();
//
//     const formData = new FormData(event.target);
//     const employeeData = Object.fromEntries(formData.entries());
//
//     try {
//         const response = await fetch('/api/employees/create', {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json',
//             },
//             body: JSON.stringify(employeeData),
//         });
//
//         if (response.ok) {
//             console.log('Сотрудник успешно добавлен!');
//         } else {
//             console.error('Произошла ошибка при добавлении сотрудника.');
//         }
//     } catch (error) {
//         console.error('Произошла ошибка:', error);
//     }
// });