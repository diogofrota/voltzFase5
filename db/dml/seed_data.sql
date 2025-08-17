/* =========================================================
   Seeds de desenvolvimento (dados de exemplo)
   ========================================================= */
-- Criptomoedas
INSERT INTO CRIPTOMOEDA (NOME, SIGLA, VALOR_ATUAL) VALUES ('Bitcoin',  'BTC', 350000.00);
INSERT INTO CRIPTOMOEDA (NOME, SIGLA, VALOR_ATUAL) VALUES ('Ethereum', 'ETH',  18000.00);
INSERT INTO CRIPTOMOEDA (NOME, SIGLA, VALOR_ATUAL) VALUES ('Solana',   'SOL',    700.00);

-- Usuários
INSERT INTO USUARIO (NOME, CPF, TELEFONE, EMAIL, SENHA)
VALUES ('Ana Silva',  '12345678901', '21999990000', 'ana.silva@email.com', 'hash_da_senha');

INSERT INTO USUARIO (NOME, CPF, TELEFONE, EMAIL, SENHA)
VALUES ('João Souza', '98765432100', '21988880000', 'joao.souza@email.com', 'hash_da_senha');

-- Aplicações (ajuste os IDs se necessário)
INSERT INTO APLICACAO (ID_USUARIO, ID_CRIPT, VALORINVESTIDO)
VALUES (1, 1, 1000.00);

INSERT INTO APLICACAO (ID_USUARIO, ID_CRIPT, VALORINVESTIDO)
VALUES (1, 2,  500.00);

INSERT INTO APLICACAO (ID_USUARIO, ID_CRIPT, VALORINVESTIDO)
VALUES (2, 1,  250.00);

COMMIT;
