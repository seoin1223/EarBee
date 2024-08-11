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
    document.getElementById('profile').textContent=user.id;


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
            case 'dashboard':
                contentDiv.innerHTML = '<h1>Dashboard Content</h1><p>Here is your dashboard.</p>';
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


function myPage(contentDiv,user){
    contentDiv.innerHTML = `
        <form id="myForm">
            <div>
                <span>ID : </span>
                <input name ="id" value=${user.id}>            
            </div>
            <input name ="num" type="hidden" readonly disabled value= ${user.num}>
            <div>
                <span>Password : </span>
                <input name ="password" type="password" placeholder="Enter your password"/>
            </div> 
            
            <button type="button" onclick="userCheck()"> 비밀번호 확인</button>
        </form>
    `;
}

function userCheck(){
    var form = document.getElementById('myForm');
    var formData = new FormData(form);
    fetch("/main/checkUserMyPage",{
        method : 'post',
        body : formData
    })
        .then(response => response.json())
        .then(data => {
            alert("yes")
        }).catch(error => {
            alert("error")
    })
}
