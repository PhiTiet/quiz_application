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
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(json)
    });
    const body = await response.json();

    event.style.backgroundColor = body ? "green" : "red";

}