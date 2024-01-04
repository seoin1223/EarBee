// 엔터키 이벤트
function handleKeyPress(event) {
    if (event.key === 'Enter') {
        event.preventDefault()
        // 엔터 키인 경우 검색 함수 호출
        search();
    }
}

// 모달창 이벤트
const Modal = document.getElementById('Modal');
document.querySelector('#inputNumber').addEventListener('keypress', handleKeyPress);

if (Modal) {
    Modal.addEventListener('show.bs.modal', event => {
        // 트리거 버튼 찾기
        const button = event.relatedTarget;

        // 데이터 가져오기
        const modalType = button.getAttribute("data-bs-type");

        // 모달창에 데이터 반영
        document.querySelector('#selectName').innerText = button.getAttribute("data-bs-name");
        // type 별로 placeholder 바꾸기 및 현재 폼이 어떤 RestApi을 호출할 것인지 form에 data-sort-type을 지정함
        if (modalType !== 'business') {
            document.getElementById("inputNumber").value = "";
            document.querySelector('#inputNumber').removeAttribute("placeholder");
            document.querySelector('#searchType').setAttribute('data-sort-type', 'address')
            document.querySelector('#inputNumber').setAttribute('type', 'text');
        } else {

            document.querySelector('#inputNumber').setAttribute('placeholder', "- 제거해서 입력해주세요");
            document.querySelector('#searchType').setAttribute('data-sort-type', 'businessNum')
            document.querySelector('#inputNumber').setAttribute('type', 'number');

        }

    });
}




function search() { // 조회가 사업자 조회인지 주소 검색인지 분류
    const dataSortType = document.querySelector('#searchType').getAttribute('data-sort-type');

    if (!(dataSortType !== 'businessNum')) {
        searchBusiness();
    }else{
        searchAddr();
    }
}

// 사업자 번호 텍스트 길이 확인
function isBusinessNumValid(businessNum) {
    // 문자열의 길이를 확인하여 유효성을 검사합니다.
    if (businessNum.length !== 10) {
        alert("사업자 번호를 확인하세요");
        $('#inputNumber').val('').focus();
        return false
    }
    // 유효한 경우 true 반환
    return true;
}




function searchBusiness(){
    const businessNum = document.querySelector('#inputNumber').value;

    if(!isBusinessNumValid(businessNum)){
        return false;
    } // 사업자 번호가 정상적인지 체크

    const url = "/api/business/search";
    const body = {
        b_no: businessNum
    }

    fetch(url, {
        method: "post",
        body: JSON.stringify(body),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(response => {
        if (response.ok) {
            return response.json();
        }else {
            alert("사업자 번호를 확인하세요");
            $('#inputNumber').val('').focus();
        }
    }).then(data => {
        if(data!=null) {
            // conso0le.log('Received data:', data);
            $('#businessNum').val(data.b_no); // 메인 폼에 사업자 번호 삽입
            $("#inputNumber").val('');
            $('#Modal').modal('hide'); // 모달창 숨기기
        }else{
            return false;
        }

    }).catch(error => {
        return false;

    });

}

function searchAddr(){
    const addr = document.querySelector('#inputNumber').value;

    const url = "/api/business/addr";
    const body = {
        keyword: addr
    }

    fetch(url, {
        method: "post",
        body: JSON.stringify(body),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(response => {
        console.log("정상적으로 확인 환요")
        return response.json();

    }).then(data => {
        if(data!=null) {
            console.log('Received data:', data);

            $('#Modal').modal('hide'); // 모달창 숨기기
        }else{
            return false;
        }

    }).catch(error => {
        return false;

    });
}