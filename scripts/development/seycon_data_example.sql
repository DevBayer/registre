--USUARIOS
INSERT INTO SC_WL_USUARI (USU_CODI,USU_PASS,USU_NOM) VALUES ('earrivi','earrivi','Eduardo Arrivi');
INSERT INTO SC_WL_USUARI (USU_CODI,USU_PASS,USU_NOM) VALUES ('mgonzalez','mgonzalez','Marilen Gonzalez');
INSERT INTO SC_WL_USUARI (USU_CODI,USU_PASS,USU_NOM) VALUES ('rwe_admin','rwe_admin','Usuario rwe_admin');
INSERT INTO SC_WL_USUARI (USU_CODI,USU_PASS,USU_NOM) VALUES ('rwe_lopd','rwe_lopd','Usuario rwe_lopd');
INSERT INTO SC_WL_USUARI (USU_CODI,USU_PASS,USU_NOM) VALUES ('rwe_usuari','rwe_usuari','Usuario rwe_usuari');

--USUARIOS-GRUPOS
INSERT INTO SC_WL_USUGRU (UGR_CODUSU,UGR_CODGRU) VALUES ('earrivi','RWE_SUPERADMIN');
INSERT INTO SC_WL_USUGRU (UGR_CODUSU,UGR_CODGRU) VALUES ('mgonzalez','RWE_SUPERADMIN');
INSERT INTO SC_WL_USUGRU (UGR_CODUSU,UGR_CODGRU) VALUES ('jpernia','RWE_ADMIN');
INSERT INTO SC_WL_USUGRU (UGR_CODUSU,UGR_CODGRU) VALUES ('fsalas','RWE_ADMIN');
INSERT INTO SC_WL_USUGRU (UGR_CODUSU,UGR_CODGRU) VALUES ('rwe_admin','RWE_ADMIN');
INSERT INTO SC_WL_USUGRU (UGR_CODUSU,UGR_CODGRU) VALUES ('rwe_usuari','RWE_USUARI');
