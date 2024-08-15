function check_userId(){
    let userid = document.getElementById("userid").value.trim();
    let useridInput = document.getElementById("checkId");
    if (userid === "") {
        alert("id를 입력해주세요");
        return false;
    }

    let url = '/main/check_id';
    let data = { id:userid};

    fetch(url, {
        method : 'POST',
        headers: {
            'Content-Type' : 'application/json',
        },
        body : JSON.stringify(data),
    }).then(response => {
        if(response.ok){
            alert("사용 가능한 아이디 입니다");
            useridInput.value="ok";
        }else{
            alert("중복된 아이디 입니다.");
            useridInput.value="";
        }
    }).catch(error => {
        console.error("Error", error);
        alert("서버와의 통신 중 오류가 발생하였습니다.");
    })
    return false;
}

function check_userAlias(){
    let alias = document.getElementById("alias").value.trim();
    let userAliasInput = document.getElementById("checkAlias");
    if(alias === ""){
        alert("별칭을 입력해주세요")
    }
    let data = {alias: alias}
    fetch(("/main/check_alias"), {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    }).then(response => {
        if (response.ok) {
            alert("사용가능한 별칭입니다");
            userAliasInput.value="ok";
        } else {
            alert("이미 중복된 별칭입니다");
            userAliasInput.value="";
        }
    }).catch(error => {
        alert("서버와의 통신 중 오류가 발생하였습니다: "+error);
    })
    return false;
}



function check_join() {
    let id = document.getElementById("checkId").value.trim();
    let alias = document.getElementById("checkAlias").value.trim();
    let form = document.getElementById("joinForm");

    let idMessage = "";
    let aliasMessage = "";

    if (id !== "ok") {
        idMessage = "id 중복확인 해주세요";
    }

    if (alias !== "ok") {
        aliasMessage = "별칭 중복확인 해주세요";
    }

    if (idMessage || aliasMessage) {
        // 두 메시지 모두 표시
        alert((idMessage ? idMessage + "\n" : "") + (aliasMessage ? aliasMessage : ""));
    } else {
        // 모든 조건이 충족된 경우 폼 제출
        form.submit();
    }
}