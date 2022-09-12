/**
 * 
 */

const alertElement = document.getElementById('alertdiv');

const alert = (message, type) => {
  const wrapper = document.createElement('div')
  wrapper.innerHTML = [
    `<div class="alert alert-${type} alert-dismissible" role="alert">`,
    `   <div id="alert-message">${message}</div>`,
    '   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>',
    '</div>'
  ].join('');

  alertElement.append(wrapper)
}


const alertTrigger = document.getElementById('registrar');

if (alertTrigger) {
  alertTrigger.addEventListener('click', () => {
	
    alert('Nice, you triggered this alert message!', 'success');

  })
								}

