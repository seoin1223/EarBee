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
        const searchResultsDiv = document.getElementById('searchResults');
        searchResultsDiv.innerHTML = '';

        // type 별로 placeholder 바꾸기 및 현재 폼이 어떤 RestApi을 호출할 것인지 form에 data-sort-type을 지정함
        if (modalType !== 'business') {
            document.getElementById("inputNumber").value = "";
            document.querySelector('#inputNumber').setAttribute("placeholder", "매곡푸르지오");
            document.querySelector('#inputNumber').setAttribute('data-current-page', "1");
            document.querySelector('#searchType').setAttribute('data-sort-type', 'address')
            document.querySelector('#inputNumber').setAttribute('type', 'text');
        } else {
            document.querySelector('#inputNumber').setAttribute('placeholder', "- 제거해서 입력해주세요");
            document.querySelector('#searchType').setAttribute('data-sort-type', 'businessNum')
            document.querySelector('#inputNumber').setAttribute('type', 'number');
        }

    });
}

// 조회 버튼이 어떤 조회버튼인지 구분하는 함수
function search() { // 조회가 사업자 조회인지 주소 검색인지 분류
    const dataSortType = document.querySelector('#searchType').getAttribute('data-sort-type');

    if (!(dataSortType !== 'businessNum')) {
        searchBusiness();
    } else {
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


function searchBusiness() {
    const businessNum = document.querySelector('#inputNumber').value;

    if (!isBusinessNumValid(businessNum)) {
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
        } else {
            alert("사업자 번호를 확인하세요");
            $('#inputNumber').val('').focus();
        }
    }).then(data => {
        if (data != null) {
            console.log('Received data:', data);
            $('#businessNum').val(data.bno); // 메인 폼에 사업자 번호 삽입
            $("#inputNumber").val('');
            $('#Modal').modal('hide'); // 모달창 숨기기
        } else {
            alert("error");
            return false;
        }

    }).catch(error => {
        return false;
    });
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





// 주소 검색 ajax
function searchAddr() {
    const addr = document.querySelector('#inputNumber');

    // searchResultsDiv.innerHTML = ''; // 이전에 표시된 결과를 초기화 -> 페이징 처리로 인해 주석
    if (!checkAddr(addr)) {
        // 검증에 실패하면 여기서 중단하고 이후 코드를 실행하지 않음
        return false;
    }

    const url = "/api/business/addr";
    const body = {
        keyword: addr.value,
        currentPage:addr.dataset.currentPage
    };

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
                displaySearchResults(data,addr.dataset.currentPage, 5); // result, currentPage, pageSize -> 후에 만약 pageSize 바꿀때 넘기는 파라메타 값을 변경하면 됨

            } else {
                // 받은 데이터가 null이면 실패로 처리
                throw new Error('Received data is null.');

            }
        })
        .catch(error => {
            console.error('Error during fetch:', error);
        });
}



function handleMouseOver(row){
    row.style.backgroundColor = '#e6f7ff'; // 마우스가 나갔을 때 배경색 초기화 (기본값으로)
}

function handleMouseLeave(row){
    row.style.backgroundColor = ''; // 마우스가 나갔을 때 배경색 초기화 (기본값으로)
}


function displaySearchResults(result, currentPage, pageSize){
    const searchResultDiv = document.getElementById('searchResults');
    clearSearchResults(searchResultDiv);

    const table = createTable(result, currentPage, pageSize);

    searchResultDiv.appendChild(table);
}

function clearSearchResults(searchResultsDiv){
    searchResultsDiv.innerHTML=''; // 결과를 초기화 -> 테이블을 지웠다가 다시 생성
}

function createTable(results, currentPage, pageSize){
    const table = document.createElement('table');
    table.classList.add('table', 'table-scroll');

    const headerRow = createHeaderRow();
    table.appendChild(headerRow);

    for (let i = 0; i < Math.min(results.length, 10); i++) {
        const result = results[i];
        const row = createRow(result);
        table.appendChild(row);
    }

    const empty1 = document.createElement('tr'); // tr
    const empty2 = document.createElement('td'); //td
    const emtDiv = document.createElement('div'); //btn
    empty2.colSpan = 2;
    emtDiv.style.height = '30px'

    empty2.appendChild(emtDiv);
    empty1.appendChild(empty2);
    table.appendChild(empty1);


    const paginationRow = createPaginationRow(currentPage, pageSize, results[0].totalCount);
    table.appendChild(paginationRow);

    return table;
}

function createHeaderRow() {
    const headerRow = document.createElement('tr');
    headerRow.classList.add('header-row'); // CSS 클래스 추가

    const nameLabel = createTableHeaderCell('우편번호', '20%'); // 너비 설정 추가
    headerRow.appendChild(nameLabel);

    const additionalInfoLabel = createTableHeaderCell('도로명 주소', '80%'); // 너비 설정 추가
    headerRow.appendChild(additionalInfoLabel);

    return headerRow;
}


function createTableHeaderCell(text, width) {
    const cell = document.createElement('th');
    cell.textContent = text;
    cell.style.width = width; // 너비 설정
    cell.style.textAlign = 'center';
    return cell;
}

function createRow(result) {
    const row = document.createElement('tr');

    // 마우스 오버 이벤트 추가
    row.addEventListener('mouseover', () => handleMouseOver(row));
    row.addEventListener('mouseleave', () => handleMouseLeave(row));

    // 클릭 이벤트 추가
    row.onclick = function (event) {
        const zipCode = document.getElementById('zipCode');
        const addr = document.getElementById('addr');
        const details = document.getElementById('detail');

        const textContentArray = Array.from(event.currentTarget.children).map(td => td.textContent);
        // 선택한 행의 정보를 array로 담아냄
        if (textContentArray.length >= 2) {
            zipCode.value = textContentArray[0];
            addr.value = textContentArray[1];
            details.readOnly = false;
            details.placeholder = "남은 주소를 채워주세요"
            $('#Modal').modal('hide'); // 모달창 숨기기
        }
    }

    // 각 열 생성 및 추가
    const nameCell = document.createElement('td');
    nameCell.textContent = result.zipNo;
    nameCell.style.width = '20%'; // 스타일 직접 설정
    nameCell.style.textAlign = 'center';
    nameCell.style.fontSize = '15px';
    row.appendChild(nameCell);

    const additionalInfoCell = document.createElement('td');
    const link = document.createElement('a');
    link.textContent = result.roadAddr; // 추가 정보가 없으면 빈 문자열 처리
    link.style.fontSize = '15px';
    additionalInfoCell.appendChild(link);
    row.appendChild(additionalInfoCell);
    return row;
}

function setButtonHoverEffect(buttonElement, hoverColor) {
    // 마우스가 올라갔을 때 배경색 변경
    buttonElement.addEventListener('mouseover', () => {
        buttonElement.style.backgroundColor = hoverColor;
    });

    // 마우스가 내려갔을 때 배경색 원래대로
    buttonElement.addEventListener('mouseout', () => {
        buttonElement.style.backgroundColor = '';
    });
}


function createPaginationRow(currentPage, pageSize, totalCount) {
    const paginationRow = document.createElement('tfoot');

    paginationRow.style.padding = '15px';
    paginationRow.style.textAlign = 'center';
    const paginationCell = document.createElement('td'); //td

    paginationCell.colSpan = 2;
    paginationCell.style.textAlign = 'center';

    const startPage = Math.floor((currentPage - 1) / pageSize) * pageSize + 1;
    const totalPages = Math.ceil(totalCount / pageSize);

    if(startPage>1){
        // 페이징 버튼 생성
        const prevButton = document.createElement('a'); //btn
        prevButton.style.display = 'inline-block';
        prevButton.textContent = '<';

        setButtonHoverEffect(prevButton,'#D3D3D3');

        // 이전 페이지로 이동하는 이벤트 핸들러 추가
        prevButton.addEventListener('click', () => {
            const addr = document.querySelector('#inputNumber');
            const currentPage = parseInt(addr.getAttribute('data-current-page'), 10);
            // 현재 페이지가 startPage보다 크면 이전 페이지 번호 계산
            if (currentPage > startPage) {
                const prevPage = currentPage - 1;
                addr.setAttribute('data-current-page', prevPage);
                searchAddr();
            } else if (currentPage === startPage && startPage > 1) {
                // 현재 페이지가 startPage와 같고 startPage가 1보다 크면 startPage - 1로 이동
                const prevPage = startPage - 1;
                addr.setAttribute('data-current-page', prevPage);
                searchAddr();
            }
        });

        paginationCell.appendChild(prevButton); //td < btn

    }

    for (let k = startPage;  k <= Math.min(startPage + pageSize - 1, totalPages); k++) {
        const pageButton = document.createElement('a'); // 페이지 버튼
        pageButton.style.display = 'inline-block';
        pageButton.textContent = k;

        // 페이지 번호를 클릭하는 이벤트 핸들러 추가
        pageButton.addEventListener('click', () => {
            const addr = document.querySelector('#inputNumber');
            addr.setAttribute('data-current-page', k);
            searchAddr();
        });

        setButtonHoverEffect(pageButton,'#D3D3D3');

        if (k.toString() === currentPage) {
            pageButton.style.color = '#ff0000'; // 현재 페이지의 배경색
        }

        paginationCell.appendChild(pageButton);
    }


    if(totalPages > startPage+pageSize-1){
        const nextButton = document.createElement('a'); // btn
        nextButton.style.display = 'inline-block';
        nextButton.textContent = '>';

        setButtonHoverEffect(nextButton,'#D3D3D3');
        // 다음 페이지로 이동하는 이벤트 핸들러 추가
        nextButton.addEventListener('click', () => {
            const addr = document.querySelector('#inputNumber');
            const currentPage = parseInt(addr.getAttribute('data-current-page'), 10);
            const nextPage = currentPage + pageSize;

            addr.setAttribute('data-current-page',nextPage);
            searchAddr()

        });

        paginationCell.appendChild(nextButton); // td < btn

    }
    paginationRow.appendChild(paginationCell)
    return paginationRow;
}

// business 빈칸 내역 확인 함수(왼쪽)
    function checkBusiness() {
        const bNo = document.getElementById('businessNum').value.trim();
        const zipCode = document.getElementById('zipCode').value.trim();
        const addr = document.getElementById('addr').value.trim();
        const detail = document.getElementById('detail').value.trim();

        if (bNo === '' || zipCode === '' || addr === '' || detail === '') {
            return false; // 빈칸이 있으면 false 반환
        } else {
            return { // 객체를 반환
                bNo: bNo,
                zipCode: zipCode,
                addr: addr,
                detail: detail
            };
        }
    }

    function businessSubmit() {
        if (checkBusiness()) {
            document.getElementById('form').submit();
        }
    }

// 왼쪽 section 중복 체크 이벤트
    function duplicateCheck() {
        const dataToSend = checkBusiness(); // checkBusiness 함수를 통해 반환된 객체를 변수에 저장
        const firstSection = document.getElementById("firstSection");
        const secondSection = document.getElementById("secondSection");
        if (!dataToSend) { // 빈칸이 있는지 확인
            alert("빈칸을 채워주세요");
        } else { // 빈칸이 없으면 AJAX 요청 보냄
            $.ajax({
                url: "/api/business/validation",
                method: "POST",
                contentType: "application/json", // Content-Type 설정
                data: JSON.stringify(dataToSend),
                dataType: "json",
            }).done((res) => {
                alert("신청할 수 있는 사업체입니다");
                // 첫번째 section의 너비를 12에서 6으로 바꿔준다.
                firstSection.classList.remove('col-12');
                firstSection.classList.add('col-6');

                secondSection.style.display = 'block';
            }).fail((err) => {
                alert("이미 중복된 사업체입니다");
            });
        }
    }

// 우측 section 방 추가 메서드
    function AddRoom() {
        const countRoom = document.getElementById('countRoom');
        let totalCount = parseInt(countRoom.value) + 1;

        // 일정 범위를 초과하는지 확인
        if (totalCount <= 15) { // 15개 이하로 제한
            countRoom.value = totalCount;

            // 새로운 div 요소 생성
            const newRow = document.createElement('div');
            newRow.className = 'row mt-2';

            // div 내부에 컬럼 요소 추가
            newRow.innerHTML = `
                                    <div class="col">
                                        <img src="#" alt="#">
                                    </div>
                                    <div class="col">
                                        <input type="number" class="form-control">
                                    </div>
                                    <div class="col">
                                        <input type="number" value="0" class="form-control">
                                    </div>
                                    <div class="col">
                                        <select class="form-control">
                                            <option value="1">Tj</option>
                                            <option value="2">금영</option>
                                        </select>
                                    </div>
                                `;

            // section 요소 내에 새로운 row를 추가합니다.
            const section = document.getElementById('secondSection');
            section.appendChild(newRow);
        } else {
            alert('최대 개수를 초과했습니다.');
        }


    document.getElementById('previewImage').addEventListener('click', function () {
        document.getElementById('fileInput').click();
    });

    document.getElementById('fileInput').addEventListener('change', function () {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                document.getElementById('previewImage').src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });
}

