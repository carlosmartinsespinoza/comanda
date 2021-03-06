PGDMP     /    1                z            MyFoods    10.20    10.20 1    :           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            ;           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            <           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false            =           1262    16393    MyFoods    DATABASE     ?   CREATE DATABASE "MyFoods" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Portuguese_Brazil.1252' LC_CTYPE = 'Portuguese_Brazil.1252';
    DROP DATABASE "MyFoods";
             postgres    false                        2615    16431    api    SCHEMA        CREATE SCHEMA api;
    DROP SCHEMA api;
             postgres    false            >           0    0 
   SCHEMA api    ACL     O   GRANT USAGE ON SCHEMA api TO web_anon;
GRANT USAGE ON SCHEMA api TO todo_user;
                  postgres    false    7                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false            ?           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    3                        3079    12924    plpgsql 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
    DROP EXTENSION plpgsql;
                  false            @           0    0    EXTENSION plpgsql    COMMENT     @   COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
                       false    1            ?            1255    25991    abre_comanda()    FUNCTION     ?  CREATE FUNCTION public.abre_comanda() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
	numero_comanda orders.ordernumber%type = 0;
BEGIN
	if (coalesce(old.idcomanda,0) = 0) then
		 select ordernumber into numero_comanda
		   from orders ord 
	 inner join mesa me
			 on ord.idmesa = new.idmesa
		  where ord.ordershipped = 0;

		/*raise notice 'o numero da comanda é %', y;*/

		if not found then
			select ordernumber into numero_comanda
					 from orders 
				 order by ordernumber 
			   desc limit 1;
		end if;
		if (numero_comanda isnull) then
			numero_comanda:= 1;
		else	
			numero_comanda := numero_comanda + 1;
		end if;	

		/*raise notice 'o numero da comanda é %', y;*/
		insert into orders(ordernumber,invoicenumber,orderdate,idmesa) values(numero_comanda,numero_comanda,current_date,new.idmesa);
		new.idcomanda := numero_comanda;
		new.ocupada := 1;
	end if;	
	RETURN NEW;
END;
$$;
 %   DROP FUNCTION public.abre_comanda();
       public       postgres    false    1    3            ?            1255    26014    auto_inc_comanda_item()    FUNCTION       CREATE FUNCTION public.auto_inc_comanda_item() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
	next_item detailS.linenumber%TYPE;
	numero_comanda orders.ordernumber%TYPE;
BEGIN
	if (coalesce(new.ordernumber,0) = 0) then
		 select coalesce(ordernumber,0) into numero_comanda
		   from orders ord 
		   where ord.mesaid = new.mesaid
		   	 and ord.ordershipped = 0;
		
		/*raise notice 'o numero da comanda é %', y;*/

		if not found then
			       select  coalesce(ordernumber,0) into numero_comanda
					 from orders 
				 order by ordernumber 
			   desc limit 1;

			if (coalesce(numero_comanda,0) = 0) then
				numero_comanda := 0;
			end if;	

			numero_comanda := numero_comanda + 1;
			insert into orders(ordernumber,
								invoicenumber,
								orderdate,
							    dinheiro,
							    troco,
							    desconto,
								mesaid,
								ordershipped) 
				 values(numero_comanda,
						numero_comanda,
						current_date,
						0,
						0,
						0,
						new.mesaid,
						0);
		end if;

		/*raise notice 'o numero da comanda é %', y;*/
	else 
		numero_comanda = new.ordernumber;
	end if;	

	update mesa 
	   set ocupada = 1,
		   idcomanda = numero_comanda,
		   valorcomanda = valorcomanda + new.price			   
	 where idmesa = new.mesaid;

	select max(coalesce(linenumber,0)) 
	  into next_item 
	  from detailS 
	 where ordernumber = new.ordernumber;

	 if (coalesce(next_item,0) = 0) then
	    next_item := 0;
	 end if;	

	new.ordernumber := numero_comanda;
	new.linenumber := next_item + 1;
	return new;
END;
$$;
 .   DROP FUNCTION public.auto_inc_comanda_item();
       public       postgres    false    1    3            ?            1259    16434    todos    TABLE     ?   CREATE TABLE api.todos (
    id integer NOT NULL,
    done boolean DEFAULT false NOT NULL,
    task text NOT NULL,
    due timestamp with time zone
);
    DROP TABLE api.todos;
       api         postgres    false    7            A           0    0    TABLE todos    ACL     X   GRANT SELECT ON TABLE api.todos TO web_anon;
GRANT ALL ON TABLE api.todos TO todo_user;
            api       postgres    false    198            ?            1259    16432    todos_id_seq    SEQUENCE     ?   CREATE SEQUENCE api.todos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE api.todos_id_seq;
       api       postgres    false    198    7            B           0    0    todos_id_seq    SEQUENCE OWNED BY     7   ALTER SEQUENCE api.todos_id_seq OWNED BY api.todos.id;
            api       postgres    false    197            C           0    0    SEQUENCE todos_id_seq    ACL     >   GRANT SELECT,USAGE ON SEQUENCE api.todos_id_seq TO todo_user;
            api       postgres    false    197            ?            1259    16453    company    TABLE     E  CREATE TABLE public.company (
    dispositivo smallint,
    arquivo character varying(99),
    idcompany smallint NOT NULL,
    name character(40),
    address character(35),
    city character(25),
    state character(2),
    zipcode character(10),
    phone character(10),
    imprimecomprovanteautomaticamente smallint
);
    DROP TABLE public.company;
       public         postgres    false    3            ?            1259    16445 	   customers    TABLE     ?  CREATE TABLE public.customers (
    custnumber integer,
    company character varying(30) NOT NULL,
    firstname character(20),
    mi character(1),
    lastname character(25),
    address1 character(35),
    address2 character(35),
    city character(25),
    state character(2),
    zipcode character(10) NOT NULL,
    phonenumber character varying(14),
    extension character(4),
    phonetype character(8)
);
    DROP TABLE public.customers;
       public         postgres    false    3            ?            1259    26128    details    TABLE     ?  CREATE TABLE public.details (
    custnumber integer,
    linenumber smallint,
    mesaid smallint,
    orderedby smallint,
    ordernumber integer NOT NULL,
    price numeric(7,2),
    productdescription character varying(99),
    productname character varying(39),
    productnumber integer,
    quantityordered numeric(7,2),
    totalcost numeric(11,2),
    backordered smallint
);
    DROP TABLE public.details;
       public         postgres    false    3            ?            1259    24593    invhist    TABLE     ?   CREATE TABLE public.invhist (
    "DATE" integer,
    transtype character(7),
    productnumber integer,
    quantity numeric(7,2),
    vendornumber integer,
    cost numeric(7,2),
    notes character(50)
);
    DROP TABLE public.invhist;
       public         postgres    false    3            ?            1259    16463    mesa    TABLE     ?   CREATE TABLE public.mesa (
    idmesa smallint NOT NULL,
    quantidadelugares smallint,
    numeromesa character(20),
    idcomanda smallint,
    ocupada integer,
    valorcomanda numeric(10,2) DEFAULT 0
);
    DROP TABLE public.mesa;
       public         postgres    false    3            ?            1259    24620    numero    TABLE     8   CREATE TABLE public.numero (
    ordernumber integer
);
    DROP TABLE public.numero;
       public         postgres    false    3            ?            1259    24617    numero_comanda    TABLE     @   CREATE TABLE public.numero_comanda (
    ordernumber integer
);
 "   DROP TABLE public.numero_comanda;
       public         postgres    false    3            ?            1259    26132    orders    TABLE     ?  CREATE TABLE public.orders (
    total numeric(7,2),
    desconto numeric(7,2),
    tipopagamento character varying(20),
    dinheiro numeric(9,2),
    troco numeric(7,2),
    custnumber integer,
    ordernumber integer,
    invoicenumber integer NOT NULL,
    orderdate date NOT NULL,
    shipaddress1 character(35),
    ordershipped smallint,
    ordernote character(80),
    mesaid smallint NOT NULL
);
    DROP TABLE public.orders;
       public         postgres    false    3            ?            1259    24576    produto    TABLE     6  CREATE TABLE public.produto (
    idproduto integer NOT NULL,
    productsku character(10),
    nome character(35),
    preco numeric(7,2),
    quantidadeemestoque numeric(7,2),
    quantidadeajustada numeric(7,2),
    custo numeric(7,2),
    picturefile character(64),
    descricao character varying(100)
);
    DROP TABLE public.produto;
       public         postgres    false    3            ?            1259    16467    state    TABLE     R   CREATE TABLE public.state (
    statecode character(2),
    name character(25)
);
    DROP TABLE public.state;
       public         postgres    false    3            ?            1259    26022    users    TABLE     ?   CREATE TABLE public.users (
    id character varying(30),
    name character(30),
    email character(30),
    status character(30),
    gender character(30)
);
    DROP TABLE public.users;
       public         postgres    false    3            ?            1259    26124 
   view_mesas    VIEW     ?   CREATE VIEW public.view_mesas AS
 SELECT mesa.idmesa,
    mesa.quantidadelugares,
    mesa.numeromesa,
    mesa.idcomanda,
    mesa.ocupada,
    mesa.valorcomanda
   FROM public.mesa
  ORDER BY mesa.numeromesa;
    DROP VIEW public.view_mesas;
       public       postgres    false    201    201    201    201    201    201    3            ?            1259    26061    view_produtos    VIEW       CREATE VIEW public.view_produtos AS
 SELECT produto.idproduto,
    produto.productsku,
    produto.nome,
    produto.preco,
    produto.quantidadeemestoque,
    produto.quantidadeajustada,
    produto.custo,
    produto.descricao
   FROM public.produto
  ORDER BY produto.nome;
     DROP VIEW public.view_produtos;
       public       postgres    false    203    203    203    203    203    203    203    203    3            ?
           2604    16437    todos id    DEFAULT     ^   ALTER TABLE ONLY api.todos ALTER COLUMN id SET DEFAULT nextval('api.todos_id_seq'::regclass);
 4   ALTER TABLE api.todos ALTER COLUMN id DROP DEFAULT;
       api       postgres    false    197    198    198            ?
           2606    16443    todos todos_pkey 
   CONSTRAINT     K   ALTER TABLE ONLY api.todos
    ADD CONSTRAINT todos_pkey PRIMARY KEY (id);
 7   ALTER TABLE ONLY api.todos DROP CONSTRAINT todos_pkey;
       api         postgres    false    198            ?
           2606    26046    mesa pk_idmesa 
   CONSTRAINT     P   ALTER TABLE ONLY public.mesa
    ADD CONSTRAINT pk_idmesa PRIMARY KEY (idmesa);
 8   ALTER TABLE ONLY public.mesa DROP CONSTRAINT pk_idmesa;
       public         postgres    false    201            ?
           1259    16456    com_poridcompany    INDEX     P   CREATE UNIQUE INDEX com_poridcompany ON public.company USING btree (idcompany);
 $   DROP INDEX public.com_poridcompany;
       public         postgres    false    200            ?
           1259    16450    cus_keycompany    INDEX     G   CREATE INDEX cus_keycompany ON public.customers USING btree (company);
 "   DROP INDEX public.cus_keycompany;
       public         postgres    false    199            ?
           1259    16448    cus_keycustnumber    INDEX     T   CREATE UNIQUE INDEX cus_keycustnumber ON public.customers USING btree (custnumber);
 %   DROP INDEX public.cus_keycustnumber;
       public         postgres    false    199            ?
           1259    16449    cus_keyfullname    INDEX     X   CREATE INDEX cus_keyfullname ON public.customers USING btree (lastname, firstname, mi);
 #   DROP INDEX public.cus_keyfullname;
       public         postgres    false    199    199    199            ?
           1259    16451    cus_keyzipcode    INDEX     G   CREATE INDEX cus_keyzipcode ON public.customers USING btree (zipcode);
 "   DROP INDEX public.cus_keyzipcode;
       public         postgres    false    199            ?
           1259    16452    cus_statekey    INDEX     C   CREATE INDEX cus_statekey ON public.customers USING btree (state);
     DROP INDEX public.cus_statekey;
       public         postgres    false    199            ?
           1259    24597    inv_keyprodnumberdate    INDEX     Z   CREATE INDEX inv_keyprodnumberdate ON public.invhist USING btree (productnumber, "DATE");
 )   DROP INDEX public.inv_keyprodnumberdate;
       public         postgres    false    204    204            ?
           1259    24596    inv_keyproductnumberdate    INDEX     k   CREATE INDEX inv_keyproductnumberdate ON public.invhist USING btree (productnumber, vendornumber, "DATE");
 ,   DROP INDEX public.inv_keyproductnumberdate;
       public         postgres    false    204    204    204            ?
           1259    24598    inv_keyvendornumberdate    INDEX     [   CREATE INDEX inv_keyvendornumberdate ON public.invhist USING btree (vendornumber, "DATE");
 +   DROP INDEX public.inv_keyvendornumberdate;
       public         postgres    false    204    204            ?
           1259    16466    mes_poridmesa    INDEX     G   CREATE UNIQUE INDEX mes_poridmesa ON public.mesa USING btree (idmesa);
 !   DROP INDEX public.mes_poridmesa;
       public         postgres    false    201            ?
           1259    24581    pro_keydescription    INDEX     F   CREATE INDEX pro_keydescription ON public.produto USING btree (nome);
 &   DROP INDEX public.pro_keydescription;
       public         postgres    false    203            ?
           1259    24579    pro_keyproductnumber    INDEX     T   CREATE UNIQUE INDEX pro_keyproductnumber ON public.produto USING btree (idproduto);
 (   DROP INDEX public.pro_keyproductnumber;
       public         postgres    false    203            ?
           1259    24580    pro_keyproductsku    INDEX     R   CREATE UNIQUE INDEX pro_keyproductsku ON public.produto USING btree (productsku);
 %   DROP INDEX public.pro_keyproductsku;
       public         postgres    false    203            ?
           1259    16470    sta_statecodekey    INDEX     N   CREATE UNIQUE INDEX sta_statecodekey ON public.state USING btree (statecode);
 $   DROP INDEX public.sta_statecodekey;
       public         postgres    false    202            ?
           2620    26016    mesa abre_comanda    TRIGGER     o   CREATE TRIGGER abre_comanda BEFORE INSERT ON public.mesa FOR EACH ROW EXECUTE PROCEDURE public.abre_comanda();
 *   DROP TRIGGER abre_comanda ON public.mesa;
       public       postgres    false    201    224            ?
           2620    26139    details auto_inc_comandaItem    TRIGGER     ?   CREATE TRIGGER "auto_inc_comandaItem" BEFORE INSERT ON public.details FOR EACH ROW EXECUTE PROCEDURE public.auto_inc_comanda_item();
 7   DROP TRIGGER "auto_inc_comandaItem" ON public.details;
       public       postgres    false    210    225           