const events = [
  ['12 nov 2026 · 15:42','Recebido pela PROGESP / Coordenação','Unidade atual'],
  ['11 nov 2026 · 10:18','Enviado para análise técnica','PROT / Núcleo de sistemas'],
  ['10 nov 2026 · 16:03','Despacho incluído no processo','Documento 04'],
  ['09 nov 2026 · 14:27','Processo recebido','PROAD / Diretoria'],
  ['06 nov 2026 · 09:11','Processo enviado para PROAD','PROGESP / Coordenação'],
  ['03 nov 2026 · 11:34','Documento inicial assinado','Documento 01'],
  ['03 nov 2026 · 10:52','Processo autuado','SEI / UFAM'],
];
document.querySelector('#timeline').innerHTML = events.map(([date,title,unit]) => `<div class="event"><time>${date}</time><strong>${title}</strong><small>${unit}</small></div>`).join('');
document.querySelector('#tree').innerHTML = `<div class="node root">Processo 23005.012345/2026-11</div><div class="node child">01 · Documento inicial<em>03 nov 2026 · assinado</em></div><div class="node child">02 · Nota técnica<em>06 nov 2026 · recebido</em></div><div class="node child">03 · Despacho<em>10 nov 2026 · incluído</em></div><div class="node child">04 · Anexo de requisitos<em>11 nov 2026 · pendente de análise</em></div>`;
const toast = (msg) => { const el=document.querySelector('#toast'); el.textContent=msg; el.classList.add('show'); setTimeout(()=>el.classList.remove('show'),2400); };
document.querySelector('#buscar').addEventListener('click',()=>{document.querySelector('.section-head h2').textContent=document.querySelector('#processo').value||'Processo sem número';toast('Consulta simulada carregada.');});
document.querySelector('#copiar').addEventListener('click',async()=>{const text='SEI Radar - liberar homologação: versão SEI/SUPER; versão mod-wssei; URL base; usuário de teste; unidade/perfil; chave/token; processo de teste; autorização de rede; definição de escopo e LGPD.';try{await navigator.clipboard.writeText(text);toast('Checklist copiado.')}catch{toast('Checklist: versão, URL, usuário, unidade, chave, processo e rede.')}});
const visual = document.querySelector('.hero-visual');
visual?.addEventListener('pointermove', (event) => {
  const box = visual.getBoundingClientRect();
  const x = (event.clientX - box.left) / box.width - .5;
  const y = (event.clientY - box.top) / box.height - .5;
  visual.style.transform = `perspective(900px) rotateY(${x * 7}deg) rotateX(${y * -5}deg)`;
});
visual?.addEventListener('pointerleave', () => { visual.style.transform = ''; });
