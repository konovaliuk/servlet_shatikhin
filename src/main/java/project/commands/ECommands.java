package project.commands;

public enum ECommands {
    login(new CommandLogin()),
    logout(new CommandLogout()),
    openReceipts(new CommandOpenReceipts()),
    openStorage(new CommandOpenStorage()),
    openAdmin(new CommandOpenAdmin()),
    createCheck(new CommandCreateCheck()),
    viewCheck(new CommandViewCheck()),
    checkEditProductPage(new CommandCheckEditProductPage()),
    editCheckProduct(new CommandEditCheckProduct()),
    removeProductCheck(new CommandRemoveProductCheck()),
    checkAddProductPage(new CommandCheckAddProductPage()),
    checkAddProduct(new CommandCheckAddProduct()),
    createUserPage(new CommandCreateUserPage()),
    createUser(new CommandCreateUser()),
    checkClose(new CommandCheckClose()),
    checkRefund(new CommandCheckRefund()),
    checkDelete(new CommandCheckDelete()),
    ;
    private final ICommand command;
    ECommands(ICommand command) {
        this.command = command;
    }
    public ICommand getCommand() { return command; }
}
