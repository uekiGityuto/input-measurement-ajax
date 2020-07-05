//import axios from 'axios'

const $start = document.getElementById('start'); 
const $task = document.getElementById('task');
const $answer = document.getElementById('answer');
const $answerSend = document.getElementById('answerSend');
const $score = document.getElementById('score');
const $scoreDisplay = document.getElementById('scoreDisplay');

// STARTボタンを押下したら、課題を表示
$start.addEventListener('click', () => {
	$task.value ='';
	axios.get('/start').then(res => {
		$task.value = res.data.task;
	})
});

// POST送信するための関数(method='post'はデフォルト引数)
function post(path, obj, method='post') {
	
	const form = document.createElement('form');
	form.method = method;
	form.action = path;
	
	for (const key in obj) {
		const hiddenField = document.createElement('input');
		hiddenField.type = 'hidden';
		hiddenField.name = key;
		hiddenField.value = obj[key];
			
		form.appendChild(hiddenField);
	}
	document.body.appendChild(form);
	form.submit();
}

// STARTボタンを押下して、30秒後にresult画面に遷移(POST送信)
$start.addEventListener('click', () => {
	console.log('30秒後にresult画面に遷移');
	setTimeout(() => {
		post('/result', {score: $score.value});
	}, 30000);
});

/*
//STARTボタンを押下して、30秒後にresult画面に遷移(GET送信)
$start.addEventListener('click', () => {
	console.log('30秒後にresult画面に遷移'); 
	setTimeout(() => { 
		location.href =`/result?score=${$score.value}`;
	}, 30000); 
});
*/

// 送信ボタンを押下したら、回答結果に応じた現在のポイントと次の課題を表示
$answerSend.addEventListener('click', () => {
	axios.post('/answer', {
		task: $task.value,
		answer: $answer.value,
		score: $score.value
	}).then(res => {
		$task.value = res.data.task;
		$answer.value = '';
		$score.value = res.data.score;
		$scoreDisplay.innerHTML = `${res.data.score}ポイント`;
		console.log('res:', res);
	}).catch(err => {
        console.log('err:', err);
    });
});
