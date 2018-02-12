# The Acme Electronics Shop

No contexto da unidade curricular de Computação Móvel foi desenvolvida uma aplicação de venda de produtos eletrónicos que permite ao utilizador escolher os produtos desejados em locais específicos da loja, juntar numa lista de compras, efetuar o seu pagamento e recolher os produtos desejados. 


![usecases](https://user-images.githubusercontent.com/15158927/36111561-7186a342-101e-11e8-876a-ad63c75f08e9.png)


## Arquitetura 

![arquitetura](https://user-images.githubusercontent.com/15158927/36111460-17d52a6c-101e-11e8-98df-fd571c9dc2b3.png)

* A versão de SDK mínima é 16 correspondente à versão Jelly Bean.
* O servidor foi desenvolvido usando NodeJs, ligado a uma base de dados postgresql. Foram usados JSON web tokens lidar com  a sessão do utilizador.
* Para a correta utilização de todas as funcionalidades da aplicação é necessário que o dispositivo tenha NFC.
* Quando o utilizador está pronto para fazer uma compra, o conteúdo é assinado usando uma função de encriptação SHA1 com RSA que posteriormente é verificado pelo servidor utilizando a chave pública do utilizador.


![screen shot 2018-02-12 at 18 00 58](https://user-images.githubusercontent.com/15158927/36111682-e05b01b4-101e-11e8-891a-6c66229ac1e6.jpg)


