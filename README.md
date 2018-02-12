# The Acme Electronics Shop

No contexto da unidade curricular de Computação Móvel foi desenvolvida uma aplicação de venda de produtos eletrónicos que permite ao utilizador escolher os produtos desejados em locais específicos da loja, juntar numa lista de compras, efetuar o seu pagamento e recolher os produtos desejados. 


## Arquitetura 
* Bullet List A versão de SDK mínima é 16 correspondente à versão Jelly Bean.
* Bullet List O servidor foi desenvolvido usando NodeJs, ligado a uma base de dados postgresql. Foram usados JSON web tokens lidar com  a sessão do utilizador.
* Bullet List Para a correta utilização de todas as funcionalidades da aplicação é necessário que o dispositivo tenha NFC.
* Bullet List Quando o utilizador está pronto para fazer uma compra, o conteúdo é assinado usando uma função de encriptação SHA1 com RSA que posteriormente é verificado pelo servidor utilizando a chave pública do utilizador.


