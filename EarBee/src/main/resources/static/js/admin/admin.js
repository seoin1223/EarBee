function searchUser(){
    fetch("/api/admin/user/search")
        .then(response=>{
            if(response.ok){
                return response.json();
            }else{
                throw new Error("유저 정보 검색을 실패하였습니다")
            }
        }).then(data=>{
            alert(data)
        }


    ).catch(error=>{
        alert("error"+error.message)
    })



}