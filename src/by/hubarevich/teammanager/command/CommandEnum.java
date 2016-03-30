/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command;

import by.hubarevich.teammanager.command.admin.*;
import by.hubarevich.teammanager.command.dispatcher.*;
import by.hubarevich.teammanager.command.hrmanager.*;
import by.hubarevich.teammanager.command.login.LoginCommand;
import by.hubarevich.teammanager.command.login.LogoutCommand;

/**
 * Class enumeration of available commands
 */

public enum CommandEnum {
    WELCOME {
        {
            this.command = new GuestCommand();
        }
    },

    EDITFORM {
        {
            this.command = new EditUserFormCommand();
        }
    },

    CREATEUSER {
        {
            this.command = new CreateUserCommand();
        }
    },

    DELETECONFIRMATION {
        {
            this.command = new DeleteConfirmCommand();
        }
    },

    DELETEUSER {
        {
            this.command = new DeleteUserCommand();
        }
    },
    EDITUSER {
        {
            this.command = new EditUserCommand();
        }
    },
    CREATEPERSON {
        {
            this.command = new CreateTeamMemberCommand();
        }
    },
    EDITPERSONFORM {
        {
            this.command = new EditTeamMemberFormCommand();
        }
    },
    EDITTEAMMEMBER {
        {
            this.command = new EditTeamMemberCommand();
        }
    },

    DELETEPERSON {
        {
            this.command = new DeleteTeamMemberCommand();
        }
    },
    REFRESHFLIGHT {
        {
            this.command = new RefreshFlightCommand();
        }
    },
    CREATEFLIGHT {
        {
            this.command = new CreateFlightCommand();
        }
    },

    CREATEFORM {
        {
            this.command = new CreateFormCommand();
        }
    },
    CREATEFLIGHTFORM {
        {
            this.command = new CreateFlightFormCommand();
        }
    },
    EDITFLIGHTFORM {
        {
            this.command = new EditFlightFormCommand();
        }
    },
    EDITTEAMFORM {
        {
            this.command = new EditTeamFormCommand();
        }
    },
    EDITTEAM {
        {
            this.command = new EditTeamCommand();
        }
    },

    EDITFLIGHT {
        {
            this.command = new EditFlightCommand();
        }
    },
    CANCELFLIGHT {
        {
            this.command = new CancelFlightCommand();
        }
    },
    CREATETEAMFORM {
        {
            this.command = new CreateTeamFormCommand();
        }
    },
    CREATETEAM {
        {
            this.command = new CreateTeamCommand();
        }
    },

    BACKTOMAIN {
        {
            this.command = new BackToMainCommand();
        }
    },

    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    };
    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
