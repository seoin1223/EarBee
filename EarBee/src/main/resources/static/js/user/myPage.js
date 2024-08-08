// nav-link 요소들에 대해 click 이벤트 리스너 추가 (네비
document.querySelectorAll('.nav-link').forEach(link => {
    link.addEventListener('click', function () {
        // 모든 링크에서 'active' 클래스 제거
        document.querySelectorAll('.nav-link').forEach(link => link.classList.remove('active'));
        // 클릭한 링크에 'active' 클래스 추가
        this.classList.add('active');
    });
});



