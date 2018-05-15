window.com_albertosoto_magnolia_bigdata_terminal_RTerminal = function() {
  var element = $(this.getElement());
  element.attr("tabindex", "0"); //set tabindex so that the terminal never loses focus
  element.focus();
  var self = this;
  var prompt = 'mgnl> ';
  var empty_prompt = '      ';
  var buffered_command = '';
  var suggestions = {};
  var binding = {};
  var multiline_mode = false;

  this.onKeyDown = function(e, term) {
      //Apparently, in Google Chrome when Shift + Enter is pressed the key code is 10, not 13
      if ((e.keyCode == 10 || e.keyCode == 13) && e.shiftKey) {
          self.handleMultilineMode(term);
          return false;
         }
      };

  this.handleMultilineMode = function(term) {
      if(!multiline_mode) {
          multiline_mode = true;
          term.echo(prompt + term.get_command());
          term.set_prompt(empty_prompt);
       } else {
          term.echo(empty_prompt + term.get_command());
       }

       buffered_command += '\n' + term.get_command();
       term.set_command('');
       self.saveStatus(term.export_view(), term.history().data());
  }

  this.restoreStatus = function(term) {
    //for some reason history contains items not present in the terminal we want to restore
    term.history().clear();
    if (self.getState().view != '') {
      var savedView = eval("(" + self.getState().view + ")");

      var linesCount = savedView.lines.length;

      for ( var j = 0; j < linesCount; j++) {
        var line = savedView.lines[j][0];
        if (line == self.getState().greetings) {
          continue;
        }
        term.echo(line);
      }

      var savedHistory = eval("(" + self.getState().history + ")")
      for ( var i = 0; i < savedHistory.length; i++) {
        term.history().append(savedHistory[i]);
      }
    }
  };


  this.onExecute = function(command, term) {
       if (command.length != 0 || buffered_command.length != 0) {
        try {
          term.pause()
          if(buffered_command.length == 0) {
            self.executeCommand(command);
          } else {
            buffered_command += '\n' + command;
            self.executeCommand(buffered_command);
          }
          //reset terminal to initial state
          buffered_command = '';
          multiline_mode = false;
          term.set_prompt(prompt);

        } catch (e) {
          term.error(new String(e));
        }
      }
    }

  this.autocompleteSuggestions = function(terminal, command, callback) {
      if(command.lastIndexOf('.') != -1) {
          var key = command.substr(0, command.lastIndexOf('.'));
          if(key in suggestions) {
             callback(suggestions[key]);
          }
      } else {
          callback(binding)
      }
  }

  var terminal = element.terminal(this.onExecute, {
    greetings : this.getState().greetings,
    name : 'groovy_terminal',
    onInit : this.restoreStatus,
    prompt : prompt,
    keydown: this.onKeyDown,
    tabcompletion: true,
    completion: this.autocompleteSuggestions
  });

  this.onStateChange = function() {

    if(this.getState().binding !== '') {
        binding = eval("(" + this.getState().binding + ")");
    }

    if(this.getState().suggestions !== '') {
        suggestions = eval("(" + this.getState().suggestions + ")");
    }

    var result = this.getState().output;

    // console.log(result);
    if (this.getState().inProgress) {
        terminal.pause()
    } else {
        if (result !== '') {
            terminal.echo('====> ' + result);
            // save view and history with the latest result from command
            // execution
            this.saveStatus(terminal.export_view(), terminal.history().data())
        }
        if (terminal.paused()) {
            terminal.resume()
        }
    }
  };
}
