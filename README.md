<h1>Conversor de Artilharia para Hell Let Loose</h1>

<h2>Resumo da aplicação</h2>
Este aplicativo captura uma pequena parte da tela do usuário a cada 200ms. Com base nos valores extraídos da imagem, ele converte os dados para texto e realiza cálculos para determinar a metragem exata. O resultado é exibido em tempo real, permitindo que o jogador ajuste a artilharia no HLL com precisão, alinhando-a corretamente à distância calculada.

<h2>Nações</h2>
  - Aliados
  <br>
  - Eixo
  <br>
  - Soviéticos
  <br>
  - Britânicos
  <br><br>
Vale deixar claro que Aliado e Eixo possuem o mesmo cálculo, portanto independente do lado que estiver deixe a opção <strong>"Aliado e Eixo"</strong> selecionada para conseguir fazer os cálculos corretamente.

<h1>Configurações do jogo e como usar o conversor</h1>

<h2>Opções de vídeo</h2>
É necessário que as configurações estejam da seguinte maneira.
<br>
<strong> - Modo de tela inteira:</strong> Modo janela em tela inteira
<br>
<strong>- Resolução de tela inteira:</strong> 1920x1080
<br>
<br>

![art1](https://github.com/user-attachments/assets/649e3f7a-bf9c-490a-abcc-036432a97c33)

<h2>Utilizando o conversor</h2>
  - Primeiro você precisa saber aonde você quer enviar artilharia, nesse caso você pode fazer uma marcação pelo mapa;
  <br>
  - Após fazer a marcação, deve ajustar a mira da artilharia até a marcação com as teclas [A e D].
  <br>
  - Agora para que o projétil caia no local correto precisamos ajustar o MIL até a Distância m que está na marcação usando as teclas [W e S].
  <br>
  <br>
  
![art2](https://github.com/user-attachments/assets/089a9766-cca3-4c69-8ae7-b0a725f43153)

<br>
<br>
É importante entender que, no HLL, a artilharia não é 100% precisa.
<br>
Ela possui uma margem de aleatoriedade de 24 metros a partir do ponto de impacto marcado. Portanto, nesse caso, não faz diferença se a marcação está em 1022 metros e a distância do conversor indica 1021 metros – o impacto será praticamente no mesmo local.

A imagem mostra uma explosão do projétil aproximadamente à marcação onde enviamos artilharia.
<br>
<br>

![art3](https://github.com/user-attachments/assets/82066d06-0064-428d-9bc5-ca5d420432d1)


<h1>Ferramentas usadas no programa</h1>
<strong>Linguagem de programação:</strong> Java na versão 17.0.12
<br>
<strong>Gerenciador de dependências:</strong> Maven
<br>
<strong>Reconhecimento ótico:</strong> Tesseract OCR
<br>

<hr>

<h1>Exclarecimento sobre o programa</h1>
Esse programa <strong>não é um hack para o HLL</strong>. Ele não interage com o código do jogo, apenas <strong>captura imagens da tela</strong> e converte os valores em tempo real. <strong>Na prática, funciona como um conversor de dados</strong>, sendo muito similar a uma calculadora já existente para o HLL.
