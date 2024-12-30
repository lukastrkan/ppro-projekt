@param cz.trkan.pproprojekt.security.MyUserDetails user


@template.layout(content = @`
    @if (user != null)
        <h1>${user.getUsername()} - ${user.getPassword()}</h1>
    @endif
`)