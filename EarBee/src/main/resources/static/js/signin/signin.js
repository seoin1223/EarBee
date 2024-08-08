
function validatePhoneNumber() {

    var input1 = document.getElementById('input1').value;
    var input2 = document.getElementById('input2').value;

    // 입력 하는곳에 값이 있는지 확인
    if(input1.length !==4 || input2.length !== 4) {
        alert('핸드폰 번호 모두 입력하세요.');
        return false;
    }

    // 입력값이 숫자로만 됐는지 확인
    var regExp = /^[0-9]+$/;
    if(!regExp.test(input1) || !regExp.test(input2)) {
    alert('숫자만 입력하세요.');
    return false;
    }

    // 유효성 통과
    return true;
}

// 모달창 이벤트
const Modal = document.getElementById('Modal');


if (Modal) {
    Modal.addEventListener('show.bs.modal', event => {
        // 트리거 버튼 찾기
        const button = event.relatedTarget;

        // 데이터 가져오기
        const modalType = button.getAttribute("data-bs-type");

        // 모달창에 데이터 반영
        document.querySelector('#selectName').innerText = button.getAttribute("data-bs-name");
        const searchResultsDiv = document.getElementById('searchResults');
        searchResultsDiv.innerHTML = '';

        // type 별로 placeholder 바꾸기 및 현재 폼이 어떤 RestApi을 호출할 것인지 form에 data-sort-type을 지정함
            document.getElementById("inputNumber").value = "";
            document.querySelector('#inputNumber').setAttribute("placeholder", "매곡푸르지오");
            document.querySelector('#searchType').setAttribute('data-sort-type', 'address')
            document.querySelector('#inputNumber').setAttribute('type', 'text');


    });
}

// 조회 버튼이 어떤 조회버튼인지 구분하는 함수
function search() { // 조회가 사업자 조회인지 주소 검색인지 분류
    const dataSortType = document.querySelector('#searchType').getAttribute('data-sort-type');
    searchAddr();
}

// 주소 검색 필터링
function checkAddr(obj) {
    if (obj.value.length > 0) {
        //특수문자 제거
        const expText = /[%=><]/;
        if (expText.test(obj.value) === true) {
            alert(`특수문자를 입력 할수 없습니다.`);
            obj.value = obj.split(expText).join("");
            return false;
        }

        //특정문자열(sql예약어의 앞뒤공백포함) 제거
        const sqlArray = ["OR", "SELECT", "INSERT", "DELETE", "UPDATE", "CREATE", "DROP", "EXEC",
            "UNION", "FETCH", "DECLARE", "TRUNCATE"];

        let regex;
        for (let i = 0; i < sqlArray.length; i++) {
            regex = new RegExp(sqlArray[i], "gi");

            if (regex.test(obj.value)) {
                alert("\"" + sqlArray[i] + "\"와(과) 같은 특정문자로 검색할 수 없습니다.");
                obj.value = obj.value.replace(regex, "");
                return false;
            }
        }
    } else {
        alert(`주소를 입력해주세요`);
        return false;
    }
    return true;
}


function searchAddr() {
    const addr = document.querySelector('#inputNumber');
    const searchResultsDiv = document.getElementById('searchResults');
    searchResultsDiv.innerHTML = ''; // 이전에 표시된 결과를 초기화
    if (!checkAddr(addr)) {
        // 검증에 실패하면 여기서 중단하고 이후 코드를 실행하지 않음
        return false;
    }

    const url = "/api/business/addr";
    const body = {keyword: addr.value};

    fetch(url, {
        method: "POST",  // HTTP 메서드는 대문자로 지정
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    })
        .then(response => {
            // HTTP 응답이 성공이면 다음 then 블록으로 이동
            if (!response.ok) {
                // 응답이 실패하면 에러 처리
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            console.log("HTTP 응답 정상 확인");

            // JSON 데이터 파싱 및 반환
            return response.json();
        })
        .then(data => {
            // 서버로부터 받은 데이터 처리
            if (data != null) {
                displaySearchResults(data);
            } else {
                // 받은 데이터가 null이면 실패로 처리
                throw new Error('Received data is null.');
            }
        })
        .catch(error => {
            console.error('Error during fetch:', error);
        });
}


function displaySearchResults(results) {
    const searchResultsDiv = document.getElementById('searchResults');
    searchResultsDiv.innerHTML = ''; // 이전에 표시된 결과를 초기화


    // 결과를 표시할 테이블 생성
    const table = document.createElement('table');

    table.classList.add('table', 'table-scroll'); // Bootstrap의 테이블 스타일을 사용하려면 'table' 클래스를 추가

    // 테이블 헤더 생성
    const headerRow = document.createElement('tr');
    const nameLabel = document.createElement('th');
    nameLabel.textContent = '우편번호'; // 결과의 필드에 따라 수정
    nameLabel.style.width = '20%'; //
    nameLabel.style.textAlign = 'center'; // 텍스트 정렬 설정
    headerRow.appendChild(nameLabel);

    const additionalInfoLabel = document.createElement('th');
    additionalInfoLabel.textContent = '도로명 주소'; // 추가 정보의 필드에 따라 수정
    additionalInfoLabel.style.textAlign = 'center';
    headerRow.appendChild(additionalInfoLabel);

    // 헤더를 테이블에 추가
    table.appendChild(headerRow);

    // 결과를 순회하면서 각 행을 생성하여 추가
    results.forEach(result => {
        const row = document.createElement('tr');
        row.addEventListener('mouseover', function () {
            row.style.backgroundColor = '#e6f7ff'; // 마우스가 올라갔을 때 배경색 변경
        });

        // 마우스가 행에서 나갔을 때의 이벤트 처리
        row.addEventListener('mouseout', function () {
            row.style.backgroundColor = ''; // 마우스가 나갔을 때 배경색 초기화 (기본값으로)
        });
        row.onclick = function (event) {
            const zipCode = document.getElementById('zipCode');
            const addr = document.getElementById('addr');
            const details = document.getElementById('detail');

            const textContentArray = Array.from(event.currentTarget.children).map(td => td.textContent);
            console.log(textContentArray);
            if (textContentArray.length >= 2) {
                zipCode.value = textContentArray[0];
                addr.value = textContentArray[1];
                details.readOnly = false;
                $('#Modal').modal('hide'); // 모달창 숨기기
            }
        };

        console.log(result.zipNo);

        // 결과 필드에 따라 수정
        const nameCell = document.createElement('td');
        nameCell.textContent = result.zipNo;

        nameCell.style.width = '20%'; // 스타일 직접 설정
        nameCell.style.textAlign = 'center';
        nameCell.style.fontSize = '10px';
        row.appendChild(nameCell);

        // 추가 정보 필드에 따라 수정
        const additionalInfoCell = document.createElement('td');
        const link = document.createElement('a');
        link.textContent = result.roadAddr; // 추가 정보가 없으면 빈 문자열 처리
        link.style.fontSize='10px';
        additionalInfoCell.appendChild(link);

        row.appendChild(additionalInfoCell);

        // 행을 테이블에 추가
        table.appendChild(row);
    });

    // 테이블을 결과 표시 영역에 추가
    searchResultsDiv.appendChild(table);
}