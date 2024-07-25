PGDMP      4                |            VeterinaryManagementSystem    15.7    16.3 -    5           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            6           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            7           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            8           1262    25953    VeterinaryManagementSystem    DATABASE     �   CREATE DATABASE "VeterinaryManagementSystem" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_T�rkiye.1254';
 ,   DROP DATABASE "VeterinaryManagementSystem";
                postgres    false            �            1259    26611    animal_vaccines    TABLE     �   CREATE TABLE public.animal_vaccines (
    id bigint NOT NULL,
    application_date date NOT NULL,
    animal_id bigint,
    vaccine_id bigint NOT NULL
);
 #   DROP TABLE public.animal_vaccines;
       public         heap    postgres    false            �            1259    26610    animal_vaccines_id_seq    SEQUENCE     �   ALTER TABLE public.animal_vaccines ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.animal_vaccines_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    215            �            1259    26617    animals    TABLE     A  CREATE TABLE public.animals (
    animal_id bigint NOT NULL,
    animal_breed character varying(255),
    animal_colour character varying(255),
    animal_birthday date,
    animal_gender character varying(255),
    animal_name character varying(255),
    animal_species character varying(255),
    customer_id bigint
);
    DROP TABLE public.animals;
       public         heap    postgres    false            �            1259    26616    animals_animal_id_seq    SEQUENCE     �   ALTER TABLE public.animals ALTER COLUMN animal_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.animals_animal_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    217            �            1259    26625    appointments    TABLE     �   CREATE TABLE public.appointments (
    appointment_id bigint NOT NULL,
    appointment_date_time timestamp(6) without time zone NOT NULL,
    animal_id bigint NOT NULL,
    doctor_id bigint NOT NULL
);
     DROP TABLE public.appointments;
       public         heap    postgres    false            �            1259    26624    appointments_appointment_id_seq    SEQUENCE     �   ALTER TABLE public.appointments ALTER COLUMN appointment_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.appointments_appointment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    219            �            1259    26631    available_dates    TABLE     �   CREATE TABLE public.available_dates (
    available_date_id bigint NOT NULL,
    date date NOT NULL,
    doctor_id bigint NOT NULL
);
 #   DROP TABLE public.available_dates;
       public         heap    postgres    false            �            1259    26630 %   available_dates_available_date_id_seq    SEQUENCE     �   ALTER TABLE public.available_dates ALTER COLUMN available_date_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.available_dates_available_date_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    221            �            1259    26637 	   customers    TABLE       CREATE TABLE public.customers (
    customer_id bigint NOT NULL,
    customer_address character varying(255),
    customer_city character varying(255),
    customer_mail character varying(255),
    customer_name character varying(255),
    customer_phone character varying(255)
);
    DROP TABLE public.customers;
       public         heap    postgres    false            �            1259    26636    customers_customer_id_seq    SEQUENCE     �   ALTER TABLE public.customers ALTER COLUMN customer_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.customers_customer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    223            �            1259    26645    doctors    TABLE       CREATE TABLE public.doctors (
    doctor_id bigint NOT NULL,
    doctor_address character varying(255),
    doctor_city character varying(255),
    doctor_mail character varying(255),
    doctor_name character varying(255),
    doctor_phone character varying(255)
);
    DROP TABLE public.doctors;
       public         heap    postgres    false            �            1259    26644    doctors_doctor_id_seq    SEQUENCE     �   ALTER TABLE public.doctors ALTER COLUMN doctor_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.doctors_doctor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    225            �            1259    26653    vaccines    TABLE     �   CREATE TABLE public.vaccines (
    vaccine_id bigint NOT NULL,
    protection_finish_date date NOT NULL,
    protection_start_date date NOT NULL,
    vaccine_code character varying(255) NOT NULL,
    vaccine_name character varying(255) NOT NULL
);
    DROP TABLE public.vaccines;
       public         heap    postgres    false            �            1259    26652    vaccines_vaccine_id_seq    SEQUENCE     �   ALTER TABLE public.vaccines ALTER COLUMN vaccine_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.vaccines_vaccine_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    227            &          0    26611    animal_vaccines 
   TABLE DATA           V   COPY public.animal_vaccines (id, application_date, animal_id, vaccine_id) FROM stdin;
    public          postgres    false    215   	9       (          0    26617    animals 
   TABLE DATA           �   COPY public.animals (animal_id, animal_breed, animal_colour, animal_birthday, animal_gender, animal_name, animal_species, customer_id) FROM stdin;
    public          postgres    false    217   \9       *          0    26625    appointments 
   TABLE DATA           c   COPY public.appointments (appointment_id, appointment_date_time, animal_id, doctor_id) FROM stdin;
    public          postgres    false    219   2:       ,          0    26631    available_dates 
   TABLE DATA           M   COPY public.available_dates (available_date_id, date, doctor_id) FROM stdin;
    public          postgres    false    221   �:       .          0    26637 	   customers 
   TABLE DATA              COPY public.customers (customer_id, customer_address, customer_city, customer_mail, customer_name, customer_phone) FROM stdin;
    public          postgres    false    223   �:       0          0    26645    doctors 
   TABLE DATA           q   COPY public.doctors (doctor_id, doctor_address, doctor_city, doctor_mail, doctor_name, doctor_phone) FROM stdin;
    public          postgres    false    225   �;       2          0    26653    vaccines 
   TABLE DATA           y   COPY public.vaccines (vaccine_id, protection_finish_date, protection_start_date, vaccine_code, vaccine_name) FROM stdin;
    public          postgres    false    227   =       9           0    0    animal_vaccines_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.animal_vaccines_id_seq', 5, true);
          public          postgres    false    214            :           0    0    animals_animal_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.animals_animal_id_seq', 8, true);
          public          postgres    false    216            ;           0    0    appointments_appointment_id_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public.appointments_appointment_id_seq', 20, true);
          public          postgres    false    218            <           0    0 %   available_dates_available_date_id_seq    SEQUENCE SET     S   SELECT pg_catalog.setval('public.available_dates_available_date_id_seq', 6, true);
          public          postgres    false    220            =           0    0    customers_customer_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.customers_customer_id_seq', 6, true);
          public          postgres    false    222            >           0    0    doctors_doctor_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.doctors_doctor_id_seq', 6, true);
          public          postgres    false    224            ?           0    0    vaccines_vaccine_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.vaccines_vaccine_id_seq', 7, true);
          public          postgres    false    226            �           2606    26615 $   animal_vaccines animal_vaccines_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.animal_vaccines
    ADD CONSTRAINT animal_vaccines_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.animal_vaccines DROP CONSTRAINT animal_vaccines_pkey;
       public            postgres    false    215            �           2606    26623    animals animals_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.animals
    ADD CONSTRAINT animals_pkey PRIMARY KEY (animal_id);
 >   ALTER TABLE ONLY public.animals DROP CONSTRAINT animals_pkey;
       public            postgres    false    217            �           2606    26629    appointments appointments_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.appointments
    ADD CONSTRAINT appointments_pkey PRIMARY KEY (appointment_id);
 H   ALTER TABLE ONLY public.appointments DROP CONSTRAINT appointments_pkey;
       public            postgres    false    219            �           2606    26635 $   available_dates available_dates_pkey 
   CONSTRAINT     q   ALTER TABLE ONLY public.available_dates
    ADD CONSTRAINT available_dates_pkey PRIMARY KEY (available_date_id);
 N   ALTER TABLE ONLY public.available_dates DROP CONSTRAINT available_dates_pkey;
       public            postgres    false    221            �           2606    26643    customers customers_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (customer_id);
 B   ALTER TABLE ONLY public.customers DROP CONSTRAINT customers_pkey;
       public            postgres    false    223            �           2606    26651    doctors doctors_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.doctors
    ADD CONSTRAINT doctors_pkey PRIMARY KEY (doctor_id);
 >   ALTER TABLE ONLY public.doctors DROP CONSTRAINT doctors_pkey;
       public            postgres    false    225            �           2606    26659    vaccines vaccines_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.vaccines
    ADD CONSTRAINT vaccines_pkey PRIMARY KEY (vaccine_id);
 @   ALTER TABLE ONLY public.vaccines DROP CONSTRAINT vaccines_pkey;
       public            postgres    false    227            �           2606    26675 (   appointments fk95vepu86o8syqtux9gkr71bhy    FK CONSTRAINT     �   ALTER TABLE ONLY public.appointments
    ADD CONSTRAINT fk95vepu86o8syqtux9gkr71bhy FOREIGN KEY (animal_id) REFERENCES public.animals(animal_id);
 R   ALTER TABLE ONLY public.appointments DROP CONSTRAINT fk95vepu86o8syqtux9gkr71bhy;
       public          postgres    false    217    3206    219            �           2606    26670 #   animals fkb36lt3kj4mrbdx5btxmp4j60n    FK CONSTRAINT     �   ALTER TABLE ONLY public.animals
    ADD CONSTRAINT fkb36lt3kj4mrbdx5btxmp4j60n FOREIGN KEY (customer_id) REFERENCES public.customers(customer_id);
 M   ALTER TABLE ONLY public.animals DROP CONSTRAINT fkb36lt3kj4mrbdx5btxmp4j60n;
       public          postgres    false    223    217    3212            �           2606    26660 +   animal_vaccines fkiwvg30h9kqewspm3hj6xl2kn9    FK CONSTRAINT     �   ALTER TABLE ONLY public.animal_vaccines
    ADD CONSTRAINT fkiwvg30h9kqewspm3hj6xl2kn9 FOREIGN KEY (animal_id) REFERENCES public.animals(animal_id);
 U   ALTER TABLE ONLY public.animal_vaccines DROP CONSTRAINT fkiwvg30h9kqewspm3hj6xl2kn9;
       public          postgres    false    3206    215    217            �           2606    26680 (   appointments fkmujeo4tymoo98cmf7uj3vsv76    FK CONSTRAINT     �   ALTER TABLE ONLY public.appointments
    ADD CONSTRAINT fkmujeo4tymoo98cmf7uj3vsv76 FOREIGN KEY (doctor_id) REFERENCES public.doctors(doctor_id);
 R   ALTER TABLE ONLY public.appointments DROP CONSTRAINT fkmujeo4tymoo98cmf7uj3vsv76;
       public          postgres    false    219    225    3214            �           2606    26685 +   available_dates fknb419ilm71d71rm584rk460pk    FK CONSTRAINT     �   ALTER TABLE ONLY public.available_dates
    ADD CONSTRAINT fknb419ilm71d71rm584rk460pk FOREIGN KEY (doctor_id) REFERENCES public.doctors(doctor_id);
 U   ALTER TABLE ONLY public.available_dates DROP CONSTRAINT fknb419ilm71d71rm584rk460pk;
       public          postgres    false    221    3214    225            �           2606    26665 *   animal_vaccines fktx6d054a6qgimiblyxm4f5ue    FK CONSTRAINT     �   ALTER TABLE ONLY public.animal_vaccines
    ADD CONSTRAINT fktx6d054a6qgimiblyxm4f5ue FOREIGN KEY (vaccine_id) REFERENCES public.vaccines(vaccine_id);
 T   ALTER TABLE ONLY public.animal_vaccines DROP CONSTRAINT fktx6d054a6qgimiblyxm4f5ue;
       public          postgres    false    215    3216    227            &   C   x�-���@���K��$⊡�:��=w���9,��0?
��P�9��s��n�9)$��E����      (   �   x�5��N�@Dϳ_�X��4P�m�
TNH\�ĠH����߳����xf���pkܫ�E�QO���>��*��ģ`��;}���'9
j��;n|$�Q��Jl�l�8;����]y��¥��N��f:�t+<hf��h�[�SZ�C��p&<;6�m�+��N�^Rq����ح���X��;���s�;�F�      *   T   x�U���@�o��0�p�t��C��I��҂Bg����r�H��\4^���(̧��hJI�٫��C4MJ�?t�� ���      ,   =   x�-��	�0ѳԋ��f\L��#8���	���P�<l9�F����wފAs��*c�>$?v�      .     x�U�Mo�0����RD?��FaH��Mt�]\bhF�Ti�ƿ_*���伯��~S(�ߔa��c�����x$�[���5�E]�Ym����n@��/�`�Z��N<X�PQ�<i�������BҨ���t�N:�y��Y^���r��G6W��e��F�5Cc1tʷ3��6XOj��)�����~��{����XK������D�Z�?L	��+��pc�:d�$��nP����\�2���S{�[��,�(���"�Q}4�z�      0     x�}��N�0E��W�$V^�c�&Al�@t����`�u��4��c["�iW���9�31�9���_p���A�j��
%{d��yw����\{%��Y?�Hk����<̋2��4�	Ir8�o����^0��B~�:#�f'��ێ�Q�{�򒢬B��4%i�b��I�n1�d�F�U��t�D�(x��U��9�HV�OB�
���N�@�� �D�>��8ZO��������*
�	���A������@�E���2Wi��yp�o4zM'���}g��T1�I      2   �   x�e�=��0�k�����O�$D
�V`Ar����J�Yi���*�Q�?0���b�����F��O�H}�B'��:m���J�m�&Q�~vAnyxz���e�&����#V��,5h�.�j��#���}��ь}�J@�f�]�}��f#����U�LwI�E�L���߄�/K!�*����������.pxq�{������Ӣ(��T�     