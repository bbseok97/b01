//모드개발

//비동기
async function get1(bno){ //async -> 비동기 처리함수 명시..
    const result = await axios.get(`/replies/list/${bno}`)//await -> 비동기 호출
    //console.log(result) //
    return result.data;
}
//goLast는 마지막댓글 확인하기 위해서
//acending 오름차순
//파라미터 요청시 이런값을 사용한다.
async  function getList({bno, page, size, goLast}){
    const result =await axios.get(`/replies/list/${bno}`,{params: {page, size}})

    if(goLast){     //goLast : boolean 타입
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total / size))
        return getList({bno:bno, page:lastPage, size:size}) //재귀함수 : 자기 자신을 참조하는 것.
    }

    return result.data;
}

async function addReply(replyObj){
    const response = await axios.post(`/replies/`, replyObj)
    return response.data
}

//p.588

async function getReply(rno){
    const response = await axios.get(`/replies/${rno}`)
    console.log(response)  //
    return response.data
}

async function modifyReply(replyObj){
    //수정된 정보를 전달해준다.
    const  response = await  axios.put(`/replies/${replyObj.rno}`, replyObj)
    //보내줄 데이터를 디티오에 집어넣는다 TValue 형태로 데이터값을 넣어주면 된다
    return response.data
}
//p.588 end

async function removeReply(rno){
    const response = await axios.delete(`/replies/${rno}`)
    return response.data
}