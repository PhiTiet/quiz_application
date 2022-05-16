async function checkQuestion(event){
    var index = event.getAttribute("data-index");
    var questionString =  document.getElementById('question' + index).getAttribute("data-question");
    var answerString = $('input[name=radio' + index + ']:checked', '#form' + index).attr('data-answer');

    var json = {
        question:questionString,
        answer:answerString
    }

    const response = await fetch("/checkanswer",{
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(json)
    });
    const body = await response.text();

    if(body != "empty"){
        event.style.backgroundColor = body ? "green" : "red";
    }
    else{
        alert("Don't forget to select an answer ._.");
    }


}