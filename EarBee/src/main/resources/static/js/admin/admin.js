function searchUser(){

    fetch("/api/admin/user/search")
        .then(response=>{
            if(response.ok){
                return response.json();
            }else{
                throw new Error("유저 정보 검색을 실패하였습니다")
            }
        }).then(data=>{
            printUser(data)
        }
    ).catch(error=>{
        alert("error"+error.message)
    });
}

function printUser(data){
    const sectionBody = document.getElementById("admin_section");

    while(sectionBody.firstChild){
        sectionBody.removeChild(sectionBody.firstChild);
    }


    const headerTitles = ['번호', '아이디', '비밀번호', '이름', '이메일', '권한', 'Oauth2', 'Oauth2ID', '휴대폰 번호', '닉네임', '생성일', '변경'];
    const fieldNames = ['num', 'id', 'password', 'username', 'email', 'role', 'provider', 'providerId', 'phone', 'alias', 'created', '변경'];

    const table = document.createElement('table');
    const thead = document.createElement('thead');
    const tbody = document.createElement('tbody');
    const headerRow = document.createElement('tr');

    headerTitles.forEach((headerText) => {
        const th = document.createElement('th');
        th.textContent = headerText;
        headerRow.appendChild(th);
    });
    thead.appendChild(headerRow);

    data.forEach(user => {
        const row = document.createElement('tr');

        fieldNames.forEach((field, index) => {
            const td = document.createElement('td');
            if (index === fieldNames.length - 1) { // 마지막 열(변경 열)
                const button = document.createElement('button');
                button.textContent = '유저 정보 변경';
                button.className = 'change-button';
                button.addEventListener('click', () => {
                });
                td.appendChild(button);
            } else {
                td.textContent = user[field] !== null ? user[field] : 'N/A';
            }
            row.appendChild(td);
        });

        tbody.appendChild(row);
    });

    table.appendChild(thead);
    table.appendChild(tbody);
    sectionBody.appendChild(table);




}


