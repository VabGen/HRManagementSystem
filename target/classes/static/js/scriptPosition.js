// // function readPosition() {
// const read = document.querySelector(".read");
// read.addEventListener('onclick', (e) => {
//     console.log("api");
// })
//
// // fetch('/api/position/')
// //     .then(response => response.json())
// //     .then(data => {
// //         console.log(data);
// //         // document.getElementById('positionNameInput').value = data.name;
// //     })
// //     .catch(error => console.error('Ошибка при получении данных о должности:', error));
// // }
//
function readPosition() {
    const url = 'https://api.example.com/data';
    fetch(url, {
        method: 'GET',
        headers: {
            'Origin': 'http://localhost:8080/api/positions/',
        },
    })
        .then(response => response.json())
        .then(data => {
            console.log('Полученные данные:', data);
        })
        .catch(error => {
            console.error('Ошибка при запросе:', error);
        });
}

// function showPosition2(d) {
//     d.forEach(item => console.log(item.text));
// }
// function showPosition(d) {
//     d.forEach(item => document.querySelector('.out').innerHTML += item.text + '<br>');
// }
// readPosition(showPosition2);
//
//
// function updatePosition(button) {
//     const positionId = button.getAttribute('data-id');
//     fetch('/api/position/' + positionId)
//         .then(response => response.json())
//         .then(data => {
//             document.getElementById('positionNameInput').value = data.name;
//         })
//         .catch(error => console.error('Ошибка при получении данных о должности:', error));
// }
//
// function deletePosition(button) {
//     const positionId = button.getAttribute('data-id');
//     if (confirm('Вы уверены, что хотите удалить должность?')) {
//         fetch('/api/position/' + positionId, {
//             method: 'DELETE'
//         })
//             .then(response => {
//                 if (response.ok) {
//                     alert('Должность успешно удалена');
//                     location.reload();
//                 } else {
//                     alert('Ошибка при удалении должности');
//                 }
//             })
//             .catch(error => console.error('Ошибка при удалении должности:', error));
//     }
// }


// function addPosition() {
//     window.location.href = '/position/create';
// }
//
// // form for creating a new position
// document.getElementById('positionForm').addEventListener('submit', async (event) => {
//     event.preventDefault();
//
//     const formData = new FormData(event.target);
//     const positionData = Object.fromEntries(formData.entries());
//
//     try {
//         const response = await fetch('/api/positions/create', {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json',
//             },
//             body: JSON.stringify(positionData),
//         });
//
//         if (response.ok) {
//             console.log('Должность успешно добавлена!');
//         } else {
//             console.error('Произошла ошибка при добавлении должности.');
//         }
//     } catch (error) {
//         console.error('Произошла ошибка:', error);
//     }
// });

