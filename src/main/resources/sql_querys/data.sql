ALTER TABLE public.roles ADD UNIQUE (name);
ALTER TABLE public.users ADD UNIQUE (username);

INSERT INTO public.roles (name)  VALUES ( 'ROLE_USER') ON CONFLICT (name) DO  NOTHING;
INSERT INTO public.roles (name)  VALUES ( 'ROLE_ADMIN')  ON CONFLICT (name) DO  NOTHING;
INSERT INTO public.roles (name)  VALUES ( 'ROLE_SUPER_ADMIN')  ON CONFLICT (name) DO  NOTHING;

INSERT INTO public.users (email,first_name,password,second_name,surname,status,username)  VALUES ( 'test@test.ru','a','$2a$10$DU35n4mQfP7Jf1poxbRQy.8KKJFSefEzXcms1d5SSCEbkE2uzl0Bu','a','a','ACTIVE','a') ON CONFLICT (username) DO NOTHING ;

INSERT INTO public.user_roles (user_id,role_id)  VALUES ((SELECT id FROM public.users WHERE username ='a'),(SELECT id FROM public.roles WHERE name ='ROLE_SUPER_ADMIN') ) ON CONFLICT (user_id) DO  NOTHING ;
