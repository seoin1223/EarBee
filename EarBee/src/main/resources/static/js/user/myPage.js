// nav-link 요소들에 대해 click 이벤트 리스너 추가 (네비
document.querySelectorAll('.nav-link').forEach(link => {
    link.addEventListener('click', function () {
        // 모든 링크에서 'active' 클래스 제거
        document.querySelectorAll('.nav-link').forEach(link => link.classList.remove('active'));
        // 클릭한 링크에 'active' 클래스 추가
        this.classList.add('active');
    });
});



document.addEventListener('DOMContentLoaded', function () {
    const sidebarLinks = document.querySelectorAll('.sidebar-nav .nav-link');
    const contentDiv = document.getElementById('content');

    // 페이지 로드 시 기본적으로 'Home' 링크를 클릭한 것처럼 처리
    const defaultActiveLink = document.querySelector('.sidebar-nav .nav-link.active');

    // data-user 속성을 읽어옵니다.
    var userLink = document.querySelector('#userInfo');
    // data-user 속성에서 JSON 문자열을 가져옵니다.
    var userJson = userLink.getAttribute('data-user');
    var user = JSON.parse(userJson);
    document.getElementById('profile').textContent=user.alias;


    if (defaultActiveLink) {
        loadContent(defaultActiveLink.getAttribute('data-content'));
    }


    sidebarLinks.forEach(link => {
        link.addEventListener('click', function (event) {
            event.preventDefault(); // 링크의 기본 동작 방지

            // 모든 링크에서 'active' 클래스 제거
            sidebarLinks.forEach(link => link.classList.remove('active'));

            // 클릭한 링크에 'active' 클래스 추가
            this.classList.add('active');

            // 클릭한 링크의 data-content 속성 값을 가져와서 콘텐츠 로드
            const contentType = this.getAttribute('data-content');
            loadContent(contentType);
        });
    });

    function loadContent(type) {
        switch (type) {
            case 'home':
                myPage(contentDiv,user);
                break;
            case 'admin':
                adminPage(contentDiv);
                break;
            case 'orders':
                contentDiv.innerHTML = '<h1>Orders Content</h1><p>Manage your orders here.</p>';
                break;
            case 'products':
                contentDiv.innerHTML = '<h1>Products Content</h1><p>Browse and manage products.</p>';
                break;
            case 'customers':
                contentDiv.innerHTML = '<h1>Customers Content</h1><p>View customer details here.</p>';
                break;
            default:
                contentDiv.innerHTML = '<h1>Home Content</h1><p>Welcome to the home page.</p>';
        }
    }
});

function adminPage(contentDiv){
    contentDiv.innerHTML =
        `
        <div class="adminDiv">
            <button type="button" onclick = "management('user')"> 유저 관리 </button>
            <button type="button" onclick = "management('business')"> 사업체 관리</button>
            <button type="button" onclick = "management('reservation')"> 예약 현황</button>
            <button> 고객센터 </button>
        </div>
        `;
}

function management(check){
    window.open(`/admin/management?check=${check}`,"_blank");
}


function myPage(contentDiv,user){
    if(user.provider !=null){
        ManpageUser();
    }else{
        contentDiv.innerHTML = `
            <form id="myForm">
                <div>
                    <span>ID : </span>
                    <input name ="id" value=${user.id} autocomplete="name">            
                </div>
                <input name ="num" type="hidden" readonly disabled value= ${user.num}>
                <div>
                    <span>Password : </span>
                    <input name ="password" type="password" placeholder="Enter your password" autocomplete="current-password"/>
                </div> 
                
                <button type="button" onclick="userCheck()"> 비밀번호 확인</button>
            </form>
        `;
    }
}

function userCheck(){
    var form = document.getElementById('myForm');
    var formData = new FormData(form);
    fetch("/main/checkUserMyPage",{
        method : 'post',
        body : formData
    })
        .then(response => {
            if (response.ok) {
                return response.text(); // 응답을 텍스트로 읽기
            } else {
                throw new Error('Network response was not ok.');
            }
        })
        .then(data => {
            alert("확인 되었습니다"); // 성공적인 응답 본문 표시
            ManpageUser();
        })
        .catch(error => {
            alert("Error: " + error.message); // 오류 메시지 표시
        });
}

function ManpageUser(){
    const contentDiv = document.getElementById('content');
    var userLink = document.querySelector('#userInfo');
    var userJson = userLink.getAttribute('data-user');
    var user = JSON.parse(userJson);
    contentDiv.innerHTML=`
        <form action="/user/update" method="post" id="updateUser">
        <div>
            <input type = "hidden"name="id" id="userid" value=${user.id} readonly>
        </div>
        <div>
            <label for="alias">별칭 : </label>
            <input name="alias" id="alias" autocomplete="name">
            <button type="button" onclick="check_userAlias()">중복 체크</button>
        </div>            
        <div>
            <label for="password">비밀번호 : </label>
            <input type="password" name="password" autocomplete="current-password">
        </div>
        <div>
            <label for="phone">전화번호 : </label> 
            <label>010</label>
            <input name="phone1">
            <input name="phone2">
        </div>    
        <div>
            <button type="button" onclick="updateUser()">수정하기</button>
        </div>
        <input id="checkAlias" value="">
        </form>
`
}

function updateUser(){
    let check = document.getElementById("checkAlias").value.trim();
    let form =  document.getElementById('updateUser')
    if(check==="ok"){
        form.submit();
    }else{
        alert("중복확인을 해주세요");
    }
}
