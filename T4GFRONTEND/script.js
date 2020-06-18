function getProcessInfo() {
  var processId = document.getElementById("processId").value;
  var processStatus = document.getElementById("processStatus").value;
  
  var queryParam = '';
  
  if (processId != null && processStatus != 'NONE') {
  queryparam = '?processId='+processId+'&processStatus='+processStatus;
} else if (processId != null) {
  queryparam = '?processId='+processId;
} else if (processStatus != 'NONE') {
  queryparam = '?processStatus='+processStatus;
}

var url = 'http://mybackend.com:8080' + queryParam;

const userAction = async () => {
  const response = await fetch(url);
  const myJson = await response.json(); //extract JSON from the http response
  // TODO: Populate table with extracted data
}

}
